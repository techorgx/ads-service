package com.project.ads.interceptor

import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import io.grpc.Status
import io.jsonwebtoken.Jwts
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.lognet.springboot.grpc.GRpcGlobalInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

@GRpcGlobalInterceptor
class AuthInterceptor(
    @param:Value("\${security.token.enabled}") private val enabled: Boolean,
    @param:Value("\${security.token.bypass}") private val bypass: String,
    @param:Value("\${security.token.keyFilePath}") private val keyFilePath: String,
    private val environment: Environment,
) : ServerInterceptor {
    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        serverCall: ServerCall<ReqT, RespT>,
        metadata: Metadata,
        nextChain: ServerCallHandler<ReqT, RespT>,
    ): ServerCall.Listener<ReqT> {
        val jwtHeader = metadata.get(Metadata.Key.of(HttpHeaders.AUTHORIZATION, Metadata.ASCII_STRING_MARSHALLER))
        val activeProfiles = environment.activeProfiles
        val bypassClientId = metadata.get(Metadata.Key.of(CLIENT_ID_HEADER, Metadata.ASCII_STRING_MARSHALLER))

        if (!enabled || bypass == bypassClientId && listOf(*activeProfiles).contains("local")) {
            nextChain.startCall(serverCall, metadata)
        }

        if (jwtHeader.isNullOrEmpty() || !validateToken(jwtHeader)) {
            serverCall.close(Status.UNAUTHENTICATED.withDescription("Invalid or missing token"), metadata)
        }
        return nextChain.startCall(serverCall, metadata)
    }

    private fun validateToken(jwtHeader: String): Boolean {
        val authenticationType: String = jwtHeader.split(" ")[0]

        if (authenticationType != "Bearer") {
            logger.error("Invalid Authentication type: $authenticationType")
        }

        return try {
            Jwts.parser()
                .setSigningKey(readSecretKey())
                .parseClaimsJws(jwtHeader)
            true
        } catch (e: Exception) {
            false
        }
    }

    // Secret key should be read from Vault, I will use vault later.
    @Throws(IOException::class)
    private fun readSecretKey(): String? {
        val activeProfiles = environment.activeProfiles
        return if (listOf(*activeProfiles).contains("local")) {
            readSecretKeyFromFile()
        } else {
            null
        }
    }

    @Throws(IOException::class)
    private fun readSecretKeyFromFile(): String {
        val path = Paths.get(keyFilePath)
        val secretKeyBytes = Files.readAllBytes(path)
        return String(secretKeyBytes, StandardCharsets.UTF_8)
    }

    companion object {
        const val CLIENT_ID_HEADER = "client-id"
        val logger: Logger = LogManager.getLogger(AuthInterceptor::class)
    }
}

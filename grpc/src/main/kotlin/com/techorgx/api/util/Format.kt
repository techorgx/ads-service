package com.techorgx.api.util

import com.google.protobuf.MessageOrBuilder
import com.google.protobuf.util.JsonFormat
import java.io.IOException

@Throws(IOException::class)
fun formatPayload(messageOrBuilder: MessageOrBuilder?): String {
    return JsonFormat.printer().omittingInsignificantWhitespace().print(messageOrBuilder)
}

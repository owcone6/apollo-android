package com.apollographql.apollo.compiler.operationoutput

import com.apollographql.apollo.compiler.applyIf
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okio.buffer
import okio.source
import java.io.File

@JsonClass(generateAdapter = true)
class OperationDescriptor(
    val name: String,
    val packageName: String,
    val source: String
)

typealias OperationOutput = Map<String, OperationDescriptor>

typealias OperationList = List<OperationDescriptor>

private fun operationOutputAdapter(indent: String? = null): JsonAdapter<OperationOutput> {
  val moshi = Moshi.Builder().build()
  val type = Types.newParameterizedType(Map::class.java, String::class.java, OperationDescriptor::class.java)
  return moshi.adapter<OperationOutput>(type).applyIf(indent != null) {
    this.indent(indent!!)
  }
}

fun OperationOutput(file: File): OperationOutput {
  return try {
    file.source().buffer().use {
      operationOutputAdapter().fromJson(it)!!
    }
  } catch (e: Exception) {
    throw IllegalArgumentException("cannot parse operation output $file")
  }
}

fun OperationOutput.toJson(indent: String? = null): String {
  return operationOutputAdapter(indent).toJson(this)
}

private fun operationList(indent: String? = null): JsonAdapter<OperationList> {
  val moshi = Moshi.Builder().build()
  val type = Types.newParameterizedType(List::class.java, OperationDescriptor::class.java)
  return moshi.adapter<OperationList>(type).applyIf(indent != null) {
    this.indent(indent!!)
  }
}

fun OperationList(file: File): OperationList {
  return try {
    file.source().buffer().use {
      operationList().fromJson(it)!!
    }
  } catch (e: Exception) {
    throw IllegalArgumentException("cannot parse operation descriptor list $file")
  }
}

fun OperationList.toJson(indent: String? = null): String {
  return operationList(indent).toJson(this)
}

fun OperationOutput.findOperationId(name: String, packageName: String): String {
  val id = entries.find { it.value.name == name && it.value.packageName == packageName }?.key
  check(id != null) {
    "cannot find operation ID for '$packageName.$name', check your operationOutput.json"
  }
  return id
}
package com.github.cuzfrog.utils

import java.io.FileInputStream
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.io.BufferedOutputStream

private[cuzfrog] object FileAssistant {
  def bytesFromFile(path: String): Array[Byte] = {
    val bis = new BufferedInputStream(new FileInputStream(path))
    val byteArray = Stream.continually(bis.read).takeWhile(_ != -1).map(_.toByte).toArray
    bis.close()
    return byteArray
  }

  def bytesToFile(path: String, data: Array[Byte]): Unit = {
    val bos = new BufferedOutputStream(new FileOutputStream(path))
    Stream.continually(bos.write(data))
    bos.close()
  }
}
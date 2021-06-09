package com.example.snapup_android.data

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.example.snapup_android.data.Result.Error
import com.example.snapup_android.data.Result.Success
import com.example.snapup_android.data.model.LoggedInUser
import com.example.snapup_android.login.LoginActivity
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.UUID

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
     private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {

    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val handler = Handler()
            Thread {//子线程
                val name = encode(username)                 //md5加密
                val pass = encode(password)
                //2.1 定义Get方式要提交的路径
                val data =  "username="+ URLEncoder.encode(name, "utf-8")+"&password="+URLEncoder.encode(pass,"utf-8")+""; //请求体的内容

                //1 postget方式提交数据 区别 路径不同
                val path = "http://192.168.11.73:8080/login/LoginServlet";     //修改url

                //1 创建一个Url对象 参数就是网址
                val url = URL(path);
                //2 获取HttpURLConnection 连接对象
                val connection =  url.openConnection() as HttpURLConnection

                //2 post和get方式提交数据区别 设置请求方式是post
                connection.requestMethod = "POST"; // 默认请求是get 要大写
                // 设置连接网络的超时时间
                connection.connectTimeout = 5000;
                //3 post和get方式提交数据区别 要多设置两个请求头信息
                //设置头信息
                connection.setRequestProperty("content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("content-Length",data.length.toString())
                //4 post 把我们组拼好的数据提交给服务器 以流的形式提交
                connection.doOutput = true; //设置一个标记 允许输出
                connection.outputStream.write(data.toByteArray())

                //5 获取服务器返回的状态码
                val code = connection.getResponseCode() //200 代表获取服务器资源全部成功 206 请求部分资源成功
                if (code == 200) { //核实是否正确
                    //6 获取服务器返回的数据 以流的形式返回
                    val inputStream = connection.getInputStream()
                    //6.1 把inputStream 转换成 string
                    val content = inputStream.read()
                    val msg = Message()
                    msg.obj = content
                    mHandler.sendMessage(msg)

                }
            }.start()

            val mMsg = mHandler.obtainMessage()    //对返回的msg进行判断是否正确

            val fakeUser = LoggedInUser(UUID.randomUUID().toString(), "Jane Doe")    //UID 和 昵称
            return Success(fakeUser)

        } catch (e: Throwable) {
            return Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
    fun encode(text: String): String {
        try {
//获取md5加密对象
            val instance: MessageDigest = MessageDigest.getInstance("MD5")
//对字符串加密，返回字节数组
            val digest:ByteArray = instance.digest(text.toByteArray())
            var sb : StringBuffer = StringBuffer()
            for (b in digest) {
//获取低八位有效值
                var i :Int = b.toInt() and 0xff
//将整数转化为16进制
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
//如果是一位的话，补0
                    hexString = "0" + hexString
                }
                sb.append(hexString)
            }
            return sb.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
}

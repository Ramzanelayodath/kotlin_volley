package enzapps.com.vollley_kotlin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    val url: String = "http://fahidhassank.xyz/api/login.php"
    lateinit var req:RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         req = Volley.newRequestQueue(this)
        btn_login.setOnClickListener { Login(edt_username.text.toString(),edt_password.text.toString()) }
        txt_register.setOnClickListener {
            val intent=  Intent(this,Register::class.java)
            startActivity(intent)

        }
    }

    public fun Login(Username: String, Password: String) {
        val dialog=ProgressDialog(this)
        dialog?.setMessage("Loading")
        dialog.show()
        val StringRequest = object : StringRequest(Method.POST, url, Listener<String> { response ->
            var status:String ?=null
            val obj=JSONObject(response)
            dialog.dismiss()
            status=obj.getString("status")
            if (status.equals("1"))
            {
                Toast.makeText(applicationContext,"Login success",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(applicationContext,"Failed",Toast.LENGTH_SHORT).show()
            }

        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {
                dialog?.dismiss()
                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()
            }
        }){

                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params.put("username", Username)
                    params.put("password", Password)

                    return params
                }
        }
        req.add(StringRequest)
    }
}

package enzapps.com.vollley_kotlin

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject

class Register : AppCompatActivity() {
    val url:String="http://fahidhassank.xyz/api/register.php"
    var req : RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        req = Volley.newRequestQueue(this)
        setContentView(R.layout.activity_register)
        btn_register.setOnClickListener{
            Registeruser(edt_username.text.toString(),edt_password.text.toString(),edt_mobile.text.toString(),edt_email.text.toString())
        }

    }

    public fun Registeruser(username:String,Password:String,Mobile:String,Email:String)
    {
        val dialog: ProgressDialog? =null
        dialog?.setMessage("Please wait")
        dialog?.show()
        val StringRequest=object:StringRequest(Request.Method.POST,url,Response.Listener<String> {
            response ->
                    dialog?.dismiss()
                    var status: String? =null
                try {
                    val obj = JSONObject(response)
                     status=obj.getString("status")
                    System.out.println("st"+status)
                    if (status.equals("1"))
                    {
                        Toast.makeText(applicationContext,"Sucess...",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(applicationContext,"Registration failed",Toast.LENGTH_SHORT).show()
                    }
                }catch (e:JSONException){e.printStackTrace()}

        },object:Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                dialog?.dismiss()
                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()
            }

        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("username", username)
                params.put("password", Password)
                params.put("mobile",Mobile)
                params.put("email",Email)
                return params
            }
        }
        req?.add(StringRequest)

    }
}

package com.utad.recuperacion_

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var userList: ArrayList<User>
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userList = ArrayList()
        userAdapter = UserAdapter(this, userList)

        val listViewUsers: ListView = findViewById(R.id.listViewUsers)
        listViewUsers.adapter = userAdapter

        listViewUsers.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedUser = userList[position]

            val intent = Intent(this@MainActivity, UserDetailsActivity::class.java)
            intent.putExtra("fullName", selectedUser.fullName)
            intent.putExtra("lastName", selectedUser.lastName)
            intent.putExtra("photo", selectedUser.photo)
            intent.putExtra("company", selectedUser.company)
            startActivity(intent)
        }

        FetchUsersTask().execute()
    }

    private inner class FetchUsersTask : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void): String? {
            val url = URL("https://dummyjson.com/users")
            val connection = url.openConnection() as HttpURLConnection

            try {
                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                val stringBuilder = StringBuilder()
                var line: String?

                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }

                return stringBuilder.toString()
            } catch (e: Exception) {
                Log.e("FetchUsersTask", "Error fetching users: ${e.message}")
            } finally {
                connection.disconnect()
            }

            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if (result != null) {
                parseUserJson(result)
            } else {
                Toast.makeText(this@MainActivity, "Error fetching users", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun parseUserJson(json: String) {
        try {
            val jsonObject = JSONObject(json)
            val usersArray = jsonObject.getJSONArray("users")

            for (i in 0 until usersArray.length()) {
                val userObject = usersArray.getJSONObject(i)
                val fullName = userObject.getString("firstName")
                val lastName = userObject.getString("lastName")
                val photo = userObject.getString("image")
                val companyObject = userObject.getJSONObject("company")
                val companyName = companyObject.getString("name")

                val user = User(fullName, lastName, photo, companyName)
                userList.add(user)
            }

            userAdapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Log.e("parseUserJson", "Error parsing user JSON: ${e.message}")
        }
    }

}


package com.example.ticketmaker.Products

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.ticketmaker.R

import kotlinx.android.synthetic.main.activity_create_product.*

class AddEditProductActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.example.ticketmaker.EXTRA_ID"
        const val EXTRA_CODE = "com.example.ticketmaker.EXTRA_CODE"
        const val EXTRA_PRICE = "com.example.ticketmaker.EXTRA_PRICE"
        const val EXTRA_DESCRIPTION = "com.example.ticketmaker.EXTRA_DESCRIPTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_product)
        setSupportActionBar(toolbarCreate)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.abc_ab_share_pack_mtrl_alpha)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Product"
            edit_text_title.setText(intent.getStringExtra(EXTRA_CODE))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PRICE, 1)
            edit_text_description.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
        } else {
            title = "Add Product"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_product_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_product -> {
                saveProduct()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveProduct() {
        if (edit_text_title.text.toString().trim().isBlank() || edit_text_description.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Can not insert empty product!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_CODE, edit_text_title.text.toString())
            putExtra(EXTRA_PRICE, number_picker_priority.value)
            putExtra(EXTRA_DESCRIPTION, edit_text_description.text.toString())
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(
                    EXTRA_ID, intent.getIntExtra(
                        EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }

}

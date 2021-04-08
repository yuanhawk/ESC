package tech.sutd.indoortrackingpro.ui.mapping

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentMappingBinding

@AndroidEntryPoint
class MappingFragment : Fragment() {
    private lateinit var location: FloatArray

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentMappingBinding>(
            inflater,
            R.layout.fragment_mapping,
            container,
            false
        )

        with(binding) {
            map.setImageResource(R.drawable.map)

            map.setOnTouchListener(@SuppressLint("ClickableViewAccessibility")
            object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    if (event?.action == MotionEvent.ACTION_UP) {
                        location = floatArrayOf(event.x, event.y)
                        Log.d(ContentValues.TAG, "onCreate: ${location[0]}, ${location[1]}")
                        Toast.makeText(
                            this@MappingFragment.context,
                            "Current location is: ${location[0]}, ${location[1]}",
                            Toast.LENGTH_SHORT
                        ).show()

                        map.isEnabled = true
                        map.pos = location
                        map.invalidate()
                        return true
                    }
                    return false
                }
            })
            return binding.root
        }
    }

}
package tech.sutd.indoortrackingpro.ui.mapping

import android.accounts.Account
import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentMappingBinding
import tech.sutd.indoortrackingpro.utils.touchCoord
import javax.inject.Inject

@AndroidEntryPoint
class MappingFragment : Fragment() {

    @Inject lateinit var bundle: Bundle
    private lateinit var location: FloatArray
    private val viewModel: MappingViewModel by viewModels()
    private lateinit var binding:FragmentMappingBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentMappingBinding>(
            inflater,
            R.layout.fragment_mapping,
            container,
            false
        )
        viewModel.floorNumber.observe(viewLifecycleOwner, {
            binding.map.secondPosList = viewModel.getMappingPositionsFloor(viewModel.floorNumber.value!!)
            binding.map.invalidate()
        })

        with(binding) {
            map.setImageResource(R.drawable.map)
            //draw all the mapping points
            map.secondPosList = viewModel.getMappingPositionsFloor(viewModel.floorNumber.value!!)
            map.invalidate()
            mappingFloor.setText(viewModel.floorNumber.value.toString())
            mappingChangeFloorButton.setOnClickListener {
                viewModel.floorNumber.value = mappingFloor.text.toString().toInt()
            }

            map.setOnTouchListener(
                object : View.OnTouchListener {
                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                        if (event?.action == MotionEvent.ACTION_UP) {
                            val z = mappingFloor.text.toString().toInt().toFloat()
                            location = floatArrayOf(event.x, event.y, z)
                            Log.d(ContentValues.TAG, "onCreate: ${location[0]}, ${location[1]}")

                            bundle.putFloatArray(touchCoord, location)
                            findNavController().navigate(
                                R.id.action_mappingFragment_to_addMappingDialog,
                                bundle
                            )

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

    override fun onResume() {
        super.onResume()
        with(binding) {
            mappingFloor.setText(viewModel.floorNumber.value.toString())
        }
    }

}
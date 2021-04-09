package tech.sutd.indoortrackingpro.ui.mapping

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.AddMappingBinding
import tech.sutd.indoortrackingpro.databinding.FragmentMainBinding
import tech.sutd.indoortrackingpro.utils.coord

class AddMappingFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val binding = DataBindingUtil.inflate<AddMappingBinding>(
                inflater,
                R.layout.add_mapping,
                container,
                false
        )

        with(binding) {
            backButtonAddMapping.setOnClickListener { view ->
                if (findNavController().previousBackStackEntry?.equals(R.id.mappingFragment) == true)
                    findNavController().popBackStack(R.id.mappingFragment, false)
                findNavController().navigate(R.id.action_addMappingDialog_to_mappingFragment)
            }
            yesButtonAddMapping.setOnClickListener { view ->
                findNavController().navigate(R.id.action_yesAddMappingDialog_to_mappingFragment)
                Toast.makeText(activity,"Coordinate added!",Toast.LENGTH_SHORT).show()
            }

            xAddMapping.text = arguments?.getFloatArray(coord)?.get(0)?.toString() ?: ""
            yAddMapping.text = arguments?.getFloatArray(coord)?.get(1)?.toString() ?: ""
            isCancelable = false
        }

        return binding.root
    }
}
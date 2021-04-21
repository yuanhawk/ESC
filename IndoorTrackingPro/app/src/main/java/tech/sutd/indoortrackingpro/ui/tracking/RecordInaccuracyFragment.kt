package tech.sutd.indoortrackingpro.ui.tracking
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentRecordInaccuracyBinding
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Account_Inaccuracy
import tech.sutd.indoortrackingpro.utils.touchCoord
import tech.sutd.indoortrackingpro.utils.trackingCoord
import java.math.RoundingMode
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt
@AndroidEntryPoint
class RecordInaccuracyFragment: BottomSheetDialogFragment() {

    @Inject lateinit var realm: Realm
    lateinit var binding: FragmentRecordInaccuracyBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_record_inaccuracy, container, false)
        val touchCoord = arguments?.getFloatArray(touchCoord)
        val trackingCoord = arguments?.getFloatArray(trackingCoord)
        with(binding){
            xCoordinateRecordInaccuracy.text = touchCoord!![0].toString()
            yCoordinateRecordInaccuracy.text = touchCoord[1].toString()
            val distance = sqrt((touchCoord[0] - trackingCoord!![0]).pow(2) + (touchCoord[1] - trackingCoord[1]).pow(2))
            inaccuracy.text = distance.toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()
            backButtonRecordInaccuracies.setOnClickListener{
                if (findNavController().previousBackStackEntry?.equals(R.id.mappingFragment) == true)
                    findNavController().popBackStack(R.id.mappingFragment, false)
                else findNavController().navigate(R.id.action_recordInaccuracyFragment_to_trackingFragment)
            }
            yesButtonAddRecording.setOnClickListener{
                realm.beginTransaction()
                val inaccuracy = Account_Inaccuracy()
                inaccuracy.x = touchCoord[0].toDouble()
                inaccuracy.y = touchCoord[1].toDouble()
                inaccuracy.inaccuracy = distance.toDouble()
                realm.where(Account::class.java).findFirst()!!.Inaccuracy.add(inaccuracy)
                realm.commitTransaction()
                findNavController().navigate(R.id.action_recordInaccuracyFragment_to_trackingFragment)
            }
        }
        return binding.root
    }


}
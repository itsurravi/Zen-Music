package com.ravisharma.zen_music.splash

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ravisharma.zen_music.R
import com.ravisharma.zen_music.data.ZenPreferenceProvider
import com.ravisharma.zen_music.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var navController: NavController

    private var binding: FragmentSplashBinding? = null

    @Inject
    lateinit var preferenceProvider: ZenPreferenceProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            binding?.appIcon?.visibility = View.GONE
            binding?.linearIndicator?.visibility = View.GONE
        } else {
            binding?.progressCircular?.visibility = View.GONE
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                preferenceProvider.isOnBoardingComplete.collect {
                    if (it == null) return@collect
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S){
                        delay(400)
                    }
                    navController.navigate(
                        if (it) R.id.action_splashFragment_to_homeFragment
                        else R.id.action_splashFragment_to_onBoardingFragment
                    )
                }
            }
        }
    }
}
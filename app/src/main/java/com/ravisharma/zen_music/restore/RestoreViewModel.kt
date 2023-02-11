package com.ravisharma.zen_music.restore

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravisharma.zen_music.data.DataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RestoreViewModel @Inject constructor(
    private val context: Application,
    private val manager: DataManager
) : ViewModel() {

    private val _restoreList = mutableStateListOf<Boolean>()
    val restoreList : List<Boolean> = _restoreList

    val blackListedSongs = manager.blacklistedSongsFlow
        .onEach {
            while (_restoreList.size < it.size) _restoreList.add(false)
            while (_restoreList.size > it.size) _restoreList.removeLast()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun updateRestoreList(index: Int, isSelected: Boolean){
        if (index >= _restoreList.size) return
        _restoreList[index] = isSelected
    }

    fun restoreSongs(){
        viewModelScope.launch {
            val blacklist = blackListedSongs.value
            val toRestore = _restoreList.indices
                .filter { _restoreList[it] }
                .map { blacklist[it] }
            try {
                manager.removeFromBlacklist(toRestore)
                Toast.makeText(context,"Rescan to see all the restored songs",Toast.LENGTH_SHORT).show()
            } catch (e: Exception){
                Timber.e(e)
                Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
package com.joshowen.scrum_poker.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseFragment<Binding : ViewBinding> : Fragment() {

    //region Variables
    lateinit var binding: Binding

    private val compositeDisposable = CompositeDisposable()

    //endregion

    //region Fragment Life-Cycle Overrides
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            initArgs(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflateBinding(inflater)
        initViews()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }
    //endregion

    //region RX
    fun Disposable.autoDispose() {
        compositeDisposable.add(this)
    }
    //endregion

    //region Core Abstract/Open functions To Override

    abstract fun inflateBinding(layoutInflater: LayoutInflater): Binding

    abstract fun observeViewModel()

    open fun initArgs(arguments: Bundle) {}

    open fun initViews() {}

    //endregion
}


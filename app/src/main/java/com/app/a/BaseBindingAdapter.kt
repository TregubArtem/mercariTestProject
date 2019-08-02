package com.app.a

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * Base class to inherit when required simple way to attach views with binding
 * Because of current purposes it is not expanded to multiply types and works only with one type of view
 *
 * @param callback used to effectively update items without need to reload all the list
 */
abstract class BaseBindingAdapter<T>(callback: ItemCallback<T>) : ListAdapter<T, ViewHolder>(callback) {

    protected abstract val classAction: BindingClassActionHolder<T>

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        @Suppress("UNCHECKED_CAST")
        val bindingClass = classAction.cls as Class<ViewDataBinding>
        val binding = bindingClass.getBinding(LayoutInflater.from(parent.context), parent)
        val holder = BingingViewHolder(classAction.action, binding)

        setLayoutParams(holder.itemView, parent.resources)
        return holder
    }

    final override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        @Suppress("UNCHECKED_CAST")
        val holder = viewHolder as BingingViewHolder<T>
        holder.bind(getItem(position), position)
    }

    protected open fun setLayoutParams(target: View, resources: Resources) {
        target.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }
}

private typealias BindingAction<T> = ViewDataBinding.(item: T, position: Int) -> Unit

class BindingClassActionHolder<T>(
    val cls: Class<out ViewDataBinding>,
    val action: BindingAction<T>
                                 )

@Suppress("UNCHECKED_CAST")
inline fun <reified B : ViewDataBinding, T> classAction(
    noinline action: B.(item: T, position: Int) -> Unit
                                                       ) =
    BindingClassActionHolder(B::class.java, action as BindingAction<T>)

private class BingingViewHolder<T>(
    private val action: BindingAction<T>,
    private val binding: ViewDataBinding
                                  ) : ViewHolder(binding.root) {
    fun bind(item: T, position: Int) {
        action(binding, item, position)
    }
}
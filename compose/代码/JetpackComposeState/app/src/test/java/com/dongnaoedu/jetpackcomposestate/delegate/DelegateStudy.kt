package com.dongnaoedu.jetpackcomposestate.delegate

import org.junit.Test
import kotlin.reflect.KProperty

//类委托
//类的委托，即一个类中定义的方法实际是调用另一个类的对象的方法来实现的。
interface Base {
    fun doSomething()
}

class BaseImpl(val x: Int) : Base {
    override fun doSomething() {
        println("doSomething:$x")
    }
}

// 在 Derived 声明中，by 子句表示，将 b 保存在 Derived 的对象实例内部，
// 而且编译器将会生成继承自 Base 接口的所有方法, 并将调用转发给 b。
class Derived(b: Base) : Base by b


// 属性委托
// 属性委托指的是一个类的某个属性值不是在类中直接进行定义，而是将其托付给一个委托类，从而实现对被委托类的属性统一管理。
// by 关键字之后的表达式就是委托, 属性的 get() 方法(以及set() 方法)将被委托给这个对象的 getValue() 和 setValue() 方法。
class Example {
    var name: String by Delegate()
}

// 该类需要包含 getValue() 方法和 setValue() 方法，
// 且参数 thisRef 为进行委托的类的对象，property 为进行委托的属性的对象。
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef，这里委托${property.name}属性"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$thisRef 的 ${property.name} 属性赋值为 $value")
    }
}


class DelegateStudy {

    @Test
    fun test1() {
        val b = BaseImpl(10)
        Derived(b).doSomething()
    }

    @Test
    fun test2() {
        val e = Example()
        println("e.name:${e.name}")

        e.name = "Ning"
        println("e.name:${e.name}")
    }
}


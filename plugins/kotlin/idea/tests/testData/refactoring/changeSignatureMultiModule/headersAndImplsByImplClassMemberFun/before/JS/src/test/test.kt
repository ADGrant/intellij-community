package test

impl class C {
    impl fun foo() { }
    impl fun <caret>foo(n: Int) { }
    impl fun bar(n: Int) { }
}

fun test(c: C) {
    c.foo()
    c.foo(1)
    c.bar(1)
}
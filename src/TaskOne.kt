//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val listAngka = 1..100

    for (angka in listAngka) {

        if (angka % 3 == 0 && angka % 5 == 0) {
            print("FooBar, ")
        } else if (angka % 3 == 0) {
            print("Foo, ")
        } else if (angka % 5 == 0) {
            print("Bar, ")
        } else {
            print("$angka, ")
        }

    }

}
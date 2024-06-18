package cl.iancs.ev1.Modelo

class ItemMesa(val itemMenu: ItemMenu, var cantidad: Int) {
    fun calcularSubtotal(): Int {
        return itemMenu.precio * cantidad
    }
}
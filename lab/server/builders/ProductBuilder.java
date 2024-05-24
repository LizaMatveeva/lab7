package ifmo.lab.server.builders;

import ifmo.lab.server.models.Product;

public class ProductBuilder {
    public static Product build(){
        Product product = new Product();
        product.setName(StringFieldBuilder.build("Введите название продукта: "));
        product.setCoordinates(CoordinatesBuilder.build());
        product.setPrice(FloatBuilder.build("Введите стоимость продукта: "));
        product.setPartNumber(PartNumberBuilder.build("Введите номер партии (длина больше 25, меньше 48): "));
        product.setManufactureCost(DoubleBuilder.build("Введите стоимость мануфактуры: "));
        product.setUnitOfMeasure(UnitOfMeasureBuilder.build());
        product.setOwner(PersonBuilder.build());

        return product;
    }
}

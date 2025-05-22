import { EnumMeasurementUnit } from '../../../enum/enum.measurement.unit';

export interface FoodMaterialModel {
    id: number;
    materialId: number;
    foodId: number;
    materialName: string;
    amount: number;
    price: number;
    measurementUnit: EnumMeasurementUnit;
}

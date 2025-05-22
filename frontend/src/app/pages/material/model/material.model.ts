import { EnumMeasurementUnit } from '../../enum/enum.measurement.unit';

export interface MaterialModel {
    id: number;
    name: string;
    measurementUnit: EnumMeasurementUnit;
    price: number;
}

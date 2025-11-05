import { Station } from './station.model';
import { Carburant } from './carburant.model';

export interface HistoCarb {
  date: string;
  prix: number;
  station: Station | { id: number };
  carburant: Carburant | { id: number };
}

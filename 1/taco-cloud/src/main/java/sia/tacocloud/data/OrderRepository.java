package sia.tacocloud.data;

import sia.tacocloud.model.TacoOrder;

public interface OrderRepository {

    TacoOrder save(TacoOrder order);
    
}

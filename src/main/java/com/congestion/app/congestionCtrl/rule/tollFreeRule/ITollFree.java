package com.congestion.app.congestionCtrl.rule.tollFreeRule;

import com.congestion.app.congestionCtrl.model.TaxInputModel;

public interface ITollFree {

    public boolean apply(TaxInputModel taxInputModel);

}

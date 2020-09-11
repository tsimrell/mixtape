package com.tsimrell.service;

import com.tsimrell.domain.mixtape.base.MixtapeBase;
import com.tsimrell.domain.mixtape.change.MixtapeChange;

public interface MixtapeChangeService {
    MixtapeBase updateMixtapeBase(MixtapeBase mixtapeBase, MixtapeChange mixtapeChange);
}

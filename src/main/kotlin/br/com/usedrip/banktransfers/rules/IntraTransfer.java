package br.com.usedrip.banktransfers.rules;

import br.com.usedrip.banktransfers.dto.TransferForm;
import br.com.usedrip.banktransfers.model.Account;
import br.com.usedrip.banktransfers.service.TransferService;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class IntraTransfer extends TransferRule{
    public IntraTransfer(TransferRule proximo, @NotNull TransferService transferServer, @NotNull Account accountTo, @NotNull Account accountFor, @NotNull TransferForm transferForm) {
        super(null, transferServer, accountTo, accountFor, transferForm);
    }

    @NotNull
    @Override
    public BigDecimal getComission(@NotNull TransferForm transferForm) {
        return BigDecimal.ZERO;
    }

    @Override
    public void validateLimit(@NotNull TransferForm transferForm) {

    }

    @Override
    public boolean getProbabilityFall(@NotNull TransferForm transferForm) {
        return true;
    }

    @Override
    public boolean nextTransfer(@NotNull Account accountTo, @NotNull Account accountFor) {
        return false;
    }
}

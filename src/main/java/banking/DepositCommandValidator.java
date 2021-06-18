package banking;

public class DepositCommandValidator {
    public boolean cmd_has_valid_instruction, cmd_has_valid_payload_size, valid_id_format,
            accessing_existing_id, account_supports_deposits, valid_deposit_amount = false;
    private Bank bank;

    public DepositCommandValidator(Bank b) {
        this.bank = b;
    }

    public boolean validate(Command c) {
        String[] payload = c.getPayload();
        String shouldbe_id = payload[0];
        String shouldbe_amount = payload[1];
        if (!shouldbe_id.matches(Validator.DIGITS) && shouldbe_id.length() == 8) {
            valid_id_format = true;
            Account target = bank.getAccount(Integer.parseInt(shouldbe_id));
            if (target != null) {
                accessing_existing_id = true;
            }
            if (!(target instanceof CDAccount)) {
                account_supports_deposits = true;
            }
            if (target instanceof CheckingAccount && (Double.parseDouble(shouldbe_amount) >= 0 && Double.parseDouble(shouldbe_amount) <= 1000)) {
                valid_deposit_amount = true;
            }
            if (target instanceof SavingsAccount && (Double.parseDouble(shouldbe_amount) >= 0 && Double.parseDouble(shouldbe_amount) <= 2500)) {
                valid_deposit_amount = true;
            }
        }
        return cmd_has_valid_instruction && cmd_has_valid_payload_size && valid_id_format && accessing_existing_id && valid_deposit_amount && account_supports_deposits;
    }
}

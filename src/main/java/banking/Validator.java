package banking;

public class Validator {

    public boolean cmd_has_valid_instruction, cmd_has_valid_payload_size, valid_id_format,
            valid_apr_given, valid_init_balance, valid_type_given = false;

    private Bank bank;
    private PassTimeCommandValidator passTimeCommandValidator = new PassTimeCommandValidator(bank);
    private DepositCommandValidator depositCommandValidator = new DepositCommandValidator(bank);
    private WithdrawalCommandValidator withdrawalCommandValidator = new WithdrawalCommandValidator(bank);
    private TransferCommandValidator transferCommandValidator = new TransferCommandValidator(bank);
    private CreateCommandValidator createCommandValidator = new CreateCommandValidator(bank);

    public Validator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(Command c) {
        if (c.getInstruction().equalsIgnoreCase(c.getValidInstruction())) {
            cmd_has_valid_instruction = true;
            if (c.getValidPayloadLength().contains(c.getPayload().length)) {
                cmd_has_valid_payload_size = true;
                if (c instanceof CreateCommand) {
                    return createCommandValidator.validate(c);
                } else if (c instanceof DepositCommand) {
                    return depositCommandValidator.validate(c);
                } else if (c instanceof PassCommand) {
                    return passTimeCommandValidator.validate(c);
                } else if (c instanceof WithdrawalCommand) {
                    return withdrawalCommandValidator.validate(c);
                } else if (c instanceof TransferCommand) {
                    return transferCommandValidator.validate(c);
                }
            }
        }
        return false;
    }

}

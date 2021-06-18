package banking;

public class Validator {

    public static final String DIGITS = ".*[a-z].*";

    public boolean cmd_has_valid_instruction, cmd_has_valid_payload_size, valid_id_format,
            valid_apr_given, valid_init_balance, valid_type_given = false;

    private PassTimeCommandValidator passTimeCommandValidator;
    private DepositCommandValidator depositCommandValidator;
    private WithdrawalCommandValidator withdrawalCommandValidator;
    private TransferCommandValidator transferCommandValidator;
    private CreateCommandValidator createCommandValidator;

    public Validator(Bank bank) {
        this.passTimeCommandValidator = new PassTimeCommandValidator();
        this.depositCommandValidator = new DepositCommandValidator(bank);
        this.withdrawalCommandValidator = new WithdrawalCommandValidator(bank);
        this.transferCommandValidator = new TransferCommandValidator(bank);
        this.createCommandValidator = new CreateCommandValidator(bank);
    }

    public boolean validate(Command c) {
        boolean out = false;
        if (c.getInstruction().equalsIgnoreCase(c.getValidInstruction())) {
            cmd_has_valid_instruction = true;
            if (c.getValidPayloadLength().contains(c.getPayload().length)) {
                cmd_has_valid_payload_size = true;
                if (c instanceof CreateCommand) {
                    out = createCommandValidator.validate(c);
                } else if (c instanceof DepositCommand) {
                    out = depositCommandValidator.validate(c);
                } else if (c instanceof PassCommand) {
                    out = passTimeCommandValidator.validate(c);
                } else if (c instanceof WithdrawalCommand) {
                    out = withdrawalCommandValidator.validate(c);
                } else if (c instanceof TransferCommand) {
                    out = transferCommandValidator.validate(c);
                }
            }
        }
        return out;
    }

}

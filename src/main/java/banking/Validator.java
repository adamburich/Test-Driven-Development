package banking;

public class Validator {

    public static final String DIGITS = ".*[a-z].*";
    public static final String INTS = "[0-9]*";
    public static final String ID = "[0-9]{8}";
    public static final String DECIMALDIGITS = "(0|[1-9]\\d*)?(\\.\\d+)?";
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
        c = c.identify_type();
        if (c != null && c.getInstruction().equalsIgnoreCase(c.getValidInstruction())) {
            cmd_has_valid_instruction = true;
            if (c.getValidPayloadLength().contains(c.getPayload().length)) {
                cmd_has_valid_payload_size = true;
                boolean proceed;
                if (c instanceof CreateCommand) {
                    proceed = c.getPayload()[1].matches(ID) && c.getPayload()[2].matches(DECIMALDIGITS);
                    if (proceed) {
                        out = createCommandValidator.validate(c);
                    }
                } else if (c instanceof DepositCommand) {
                    proceed = c.getPayload()[0].matches(ID) && c.getPayload()[1].matches(DECIMALDIGITS);
                    if (proceed) {
                        out = depositCommandValidator.validate(c);
                    }
                } else if (c instanceof PassCommand) {
                    proceed = c.getPayload()[0].matches(INTS);
                    if (proceed) {
                        out = passTimeCommandValidator.validate(c);
                    }
                } else if (c instanceof WithdrawalCommand) {
                    proceed = c.getPayload()[0].matches(ID) && c.getPayload()[1].matches(DECIMALDIGITS);
                    if (proceed) {
                        out = withdrawalCommandValidator.validate(c);
                    }
                } else if (c instanceof TransferCommand) {
                    proceed = c.getPayload()[0].matches(ID) && c.getPayload()[1].matches(ID) && c.getPayload()[2].matches(DECIMALDIGITS);
                    if (proceed) {
                        out = transferCommandValidator.validate(c);
                    }
                } else {
                    return validate(c.identify_type());
                }
            }
        }
        return out;
    }

}

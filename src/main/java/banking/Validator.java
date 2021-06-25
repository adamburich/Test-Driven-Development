package banking;

public class Validator {

    public static final String DIGITS = ".*[a-z].*";
    public static final String INTS = "\\d+";
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
                if (c instanceof CreateCommand && proceed_create(c.getPayload())) {
                    out = createCommandValidator.validate(c);
                } else if (c instanceof DepositCommand && proceed_deposit_withdraw(c.getPayload())) {
                    out = depositCommandValidator.validate(c);
                } else if (c instanceof PassCommand && c.getPayload()[0].matches(INTS) && !c.getPayload()[0].contains(".")) {
                    out = passTimeCommandValidator.validate(c);
                } else if (c instanceof WithdrawalCommand && proceed_deposit_withdraw(c.getPayload())) {
                    out = withdrawalCommandValidator.validate(c);
                } else if (c instanceof TransferCommand && proceed_transfer(c.getPayload())) {
                    out = transferCommandValidator.validate(c);
                }
            }
        }
        return out;
    }

    public boolean proceed_create(String[] payload) {
        return payload[1].matches(ID) && payload[2].matches(DECIMALDIGITS);
    }

    public boolean proceed_deposit_withdraw(String[] payload) {
        return payload[0].matches(ID) && payload[1].matches(DECIMALDIGITS);
    }

    public boolean proceed_transfer(String[] payload) {
        return payload[0].matches(ID) && payload[1].matches(ID) && payload[2].matches(DECIMALDIGITS);
    }

}

package it.sosinski.accountbalance.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class contains URIs definitions used in controllers
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UriConstants {

    public static final String URI_EXPENSES = "/expenses";

    public static final String URI_INCOME = "/income";

    public static final String URI_ACCOUNT_BALANCE = "/account-balance";

    public static final String URI_LIST = "/list";

    public static final String URI_CREATE = "/create";

    public static final String URI_EXPENSES_LIST = URI_EXPENSES + URI_LIST;

    public static final String URI_INCOME_LIST = URI_INCOME + URI_LIST;

    public static final String URI_EXPENSES_CREATE = URI_EXPENSES + URI_CREATE;

    public static final String URI_INCOME_CREATE = URI_INCOME + URI_CREATE;

}

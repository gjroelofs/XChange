package com.xeiam.xchange.utils;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import java.math.RoundingMode;

/**
 * <p>
 * Utilities to provide the following to application:
 * </p>
 * <ul>
 * <li>Various shortcuts to common currencies from Joda Money</li>
 * </ul>
 * 
 * @since 0.0.1
 */
public class MoneyUtils {

  /**
   * @param value A general-purpose currency and value representation (e.g. "USD 3210.12345678")
   * @return A standard fiat currency BigMoney that can handle complex calculations and display using a scale inferred from the minor part
   * @see org.joda.money.Money For a simpler approach for fiat currencies not requiring precise calculations (e.g. display only)
   */
  public static BigMoney parseFiat(String value) {
    return BigMoney.parse(value);
  }

  /**
   * @param value A general-purpose currency and value representation (e.g. "BTC 3210.1234567800")
   * @return A standard Bitcoin currency BigMoney that can handle complex calculations using a scale of 12 regardless of the minor part
   */
  public static BigMoney parseBitcoin(String value) {

    BigMoney unscaled = BigMoney.parse(value);
    if (!unscaled.getCurrencyUnit().getCode().equals("BTC")) {
      throw new IllegalArgumentException("Must have BTC currency code");
    }
    return unscaled.withScale(12);
  }

  /**
   * @param value A whole number of satoshis (e.g. 1 provides the same as "BTC 0.00000001")
   * @return A standard Bitcoin currency BigMoney that can handle complex calculations using a scale of 12 regardless of the minor part
   * TODO Add method to include a scaling factor from a long to act as a multiplier/divisor
   */
  public static BigMoney fromSatoshi(long value) {

    BigMoney unscaled = BigMoney.ofScale(CurrencyUnit.getInstance("BTC"), value, 8);
    if (!unscaled.getCurrencyUnit().getCode().equals("BTC")) {
      throw new IllegalArgumentException("Must have BTC currency code");
    }
    return unscaled.withScale(12);
  }

  public static String formatBitcoin(BigMoney value) {

    if (!value.getCurrencyUnit().getCode().equals("BTC")) {
      throw new IllegalArgumentException("Must have BTC currency code");
    }
    return value.withScale(8, RoundingMode.HALF_UP).toString();
  }

}

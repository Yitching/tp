# Mint User Guide v2.0

## Introduction

Mint is an all-in-one money managing app that helps you track your daily expenses, set budgets and long term financial
goals(coming soon). It is optimized for use via a Command Line Interface (CLI).

As our team comprises university students, we hope that we can help fellow young adults in keeping track of their
finances.

Using this guide, you will be able to navigate the app and use all of its functionalities through
step-by-step-instructions.
:bulb:

## Table of Contents

- [Quick Start](#quickStart)
    - [Setting Up](#settingUp)
    - [Running the Programme](#runningTheProgramme)
    - [[For users new to CLI] Changing the Directory](#changingTheDirectory)
- [Features](#features)
    - [Viewing help](#help)
    - [Adding entries](#add)
    - [Adding recurring entries](#addR)
    - [Viewing entries](#view)
    - [Deleting entries](#delete)
    - [Deleting recurring entries](#deleteR)
    - [Deleting all entries](#deleteAll)
    - [Editing entries](#edit)
    - [Editing recurring entries](#editR)
    - [Viewing categories](#cat)
    - [Setting budget](#set)
    - [View monthly budget ](#budget)
    - [Exiting the program](#exit)
- [Available date formats](#dateFormat)
- [List of categories](#categoryList)
- [Command Summary](#command-summary)

## <a name="quickStart"></a>Quick Start

> Before you get started, ensure that you have Java 11 or above installed in your Computer. Once that is done, follow
> the steps below!

### <a name="settingUp"></a>Setting Up

1. Download the latest version of tp.jar from [here](https://github.com/AY2122S1-CS2113T-W11-2/tp/releases/tag/v2.0b).

2. Copy the tp.jar file to the folder you desire.

### <a name="runningTheProgramme"></a>Running the Programme

1. Open your desired Command Line Interface and ensure that you are in the directory where you saved the folder. If you
   are new to git, click [here](#changingTheDirectory) to see how you can change the directory.
2. Once you ensured you are in the correct directory, run the programme using the command `java -jar tp.jar`.
3. To test if the programme is working, type a command and press Enter to execute it. e.g., typing `help` and pressing
   Enter will display the list of commands to help you use our application.

**Some example commands you can try:**

1. Add an expense to your list: `add a/13 d/2021-12-03 n/Movie ticket c/1`
   > This command adds a Movie ticket that you have purchased for 13 dollars on December 3rd 2021 under the
   > Entertainment category

2. Exit the programme: `exit`
   > This command terminates the program.

Refer to the [Features Section](#features) below for details of each comm

### <a name="changingTheDirectory"></a>[For users new to CLI] Changing the Directory to your tp.jar file

1. Right-click on your tp.jar file and select Properties. There would be a pop up with all the information.
2. Look for the Location and copy the entire string.
3. Go back to your Command Line Interface and enter the command `cd [paste what you copied here]`
4. Mint is now at your service!

## <a name="features"></a>Features

---

Notes about the following list of commands:

- Items in square brackets are optional.

  e.g `n/NAME [d/DATE]` can be used as `n/burger d/2021-10-20` or as `n/burger`
- Parameters with tags or optional modifiers can be in any order.

  e.g. if the command specifies `n/NAME` `a/AMOUNT`, `a/AMOUNT` `n/NAME` is also acceptable.
- If a parameter is expected only once in the command but if you specify it multiple times, only the last occurrence of
  the parameter will be taken.

  e.g. if you specify `a/10 a/15`, only `a/10` will be taken.
- Extraneous parameters for commands that do not take in parameters (such as `help`, `budget` and `exit`)
  will be ignored.

  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

---

## <a name="dateFormat"></a>Acceptable date formats

Example: 5th Jaunary 2020

|Format | Example of input |
|--------|----------|
| yyyy-MM-dd | 2020-01-05|
| yyyy-M-dd  | 2020-1-05 | 
| yyyy-MM-d  | 2020-01-5 |
| yyyy-M-d | 2020-1-5 |
| dd-MM-yyyy | 05-01-2020 | 
| d-MM-yyyy | 5-01-2020 |
| d-M-yyyy | 5-1-2020 |
| dd-M-yyyy |05-1-2020 |
| dd MMM yyyy (for MMM, capitalise only the first letter, e.g. Jan) | 05 Jan 2020 |
| d MMM yyyy (for MMM, capitalise only the first letter, e.g. Jan) | 5 Jan 2020 |

## <a name="categoryList"></a>Available categories

|Category tag | Category name |
|--------|----------|
| c/0 | Food |
| c/1 | Entertainment
| c/2| Transportation
| c/3 | Household
| c/4 | Apparel
| c/5 | Beauty
| c/6 | Gift
| c/7 | Others

## <a name="help"></a>Viewing help: `help`

Shows a list of possible user commands

Format: `help`

## <a name="add"></a>Adding entries: `add`

Adds an expense or income to your tracker

Format: `add [income] n/NAME a/AMOUNT [d/DATE] [c/CATEGORY_NUMBER]`

- Adds an entry of the specified `NAME`, `DATE`, `AMOUNT` and `CATEGORY_NUMBER`
- If `income` is included after `add`, entry will be an income entry, else it will be an expense entry.
- `NAME` can be any string of characters
- `AMOUNT` is any number up to 2 decimal points.
- `DATE(optional)` can be any of the [acceptable date formats](#dateFormat).
    - If the date is not specified, the default date set would be the date of expense entry.
- `CATEGORY_NUMBER(optional)`
    - Please refer to the [available categories](#categoryList).
    - If the `CATEGORY_NUMBER` is not specified, the default `CATEGORY_NUMBER` would be C/7 which is `others`.

Examples:

- Adding a textbook that costs $15: `add n/textbook a/15`
- Adding a cheeseburger that costs $4.20 that I had on 20th April 2021 and categorize it under "
  Food": `add n/Cheese Burger a/4.2 d/2021-04-20 c/0`
- Adding the income I made from sales, amounting to $34 `add income n/Sales a/34 d/2021-02-19 c/1`

Examples and expected Output

```
add n/Textbook a/15
I've added: Expense  | OTHERS | 2021-10-28 | Textbook | $15.00
add n/Cheese burger a/4.2 d/2021-04-20 c/0
I've added: Expense  | FOOD | 2021-04-20 | Cheese burger | $4.20
add income n/Sales a/34 d/2021-02-19 c/1
I've added: Income  | WAGES | 2021-02-19 | Sales | $34.00
```

## <a name="addR"></a>Adding Recurring Entries: `addR`

Adds an expense or income to your tracker

Format: `addR [income] n/NAME a/AMOUNT [d/DATE] [c/CATEGORY_NUMBER] [i/INTERVAL] [e/END_DATE]`

- Adds an entry of the specified `NAME`, `DATE`, `AMOUNT`, `INTERVAL`, `END_DATE` and `CATEGORY_NUMBER`
- If `income` is included after `add`, entry will be an income entry, else it will be an expense entry.
- `NAME` can be any string of characters
- `AMOUNT` is any number up to 2 decimal points.
- `DATE(optional)` can be any of the [acceptable date formats](#dateFormat).
    - If the date is not specified, the default date set would be the date of expense entry.
- `CATEGORY_NUMBER(optional)` - Please refer to the [available categories](#categoryList).
    - If the `CATEGORY_NUMBER` is not specified, the default `CATEGORY_NUMBER` would be C/7 which is `others`.
- `INTERVAL` can be a string of either `MONTH` or `YEAR` depending on how often one receives the `INCOME` or has to pay
  for the expenditure. The string is not case-sensitive.
- `END_DATE(optional)` can be any of the [acceptable date formats](#dateFormat).

  If the date is not specified, the default date set would be forever.

Examples:

- `addR a/90 d/2021-12-03 n/phone bills c/3 i/MONTH`
- `addR a/5 n/phone bills c/4 i/year e/2023-10-26`
- `addR income a/10000 n/salary d/2021-10-10 i/mOnTh`

Examples and expected Output

```
addR a/90 d/2021-12-03 n/phone bills c/3 i/MONTH
I've added: Expense | HOUSEHOLD | 2021-12-03 | phone bills |-$90.00 | MONTH | Forever :D
addR a/5 n/phone bills c/4 i/year e/2023-10-26
I've added: Expense | APPAREL | 2021-10-29 | phone bills |-$5.00 | YEAR | 2023-10-26
addR income a/10000 n/salary d/2021-10-10 i/mOnTh
I've added: Income  | OTHERS | 2021-10-10 | salary | $10,000.00 | MONTH | Forever :D
```

## <a name="view"></a>Viewing all entries: `view`

Shows a list of all the expenses, each with the associated `NAME`, `DATE`, and `AMOUNT`.

Format: `view [income] [expense] [by SORTTYPE] [month MONTH] [year YEAR] [from STARTDATE [ENDDATE]] [up/ascending]`

- Views all entries with the specified `MONTH`, `YEAR`, from `STARTDATE` to `ENDDATE`, sorted by `SORTTYPE`.
- `[income](optional)` and `[expense](optional)` if appended, only shows the entries of the corresponding type.
- `SORTTYPE` can be any of the following types: `name`, `date`, `amount`, `category`
- `MONTH(optional)` can be any number from 1 to 12.
- If `MONTH` is not specified, the default will be the current month.
- `YEAR(optional)` can be any 4-digit number from 1000 to 9999.
- If `YEAR` is not specified, the default will be the current year.
- `STARTDATE(optional)` and `ENDDATE(optional)` can be any of the [acceptable date formats](#dateFormat).
- If `STARTDATE` is specified but `ENDDATE` is not specified, the default `ENDDATE` set would be the current date.
- `up(optional)` or `ascending(optional)` if appended with sort, will sort the list in ascending , else the default will
  sort the list in descending order.

Examples:

- `view`
- `view income`
- `view month 4 year 2021`
- `view from 2021-03-25 2022-01-02 by amount ascending`

Examples and expected Output:

```
view
Here is the list of your entries:
  Type  | Category |    Date    |     Name      | Amount | Every |   Until
Income  |  WAGES   | 2021-11-03 |     Sales     | $34.00 |       |
Expense |  OTHERS  | 2021-10-28 |    Netflix    |-$40.00 | MONTH | Forever :D
Expense |  OTHERS  | 2021-10-28 |      Viu      |-$30.00 | MONTH | Forever :D
Expense |  OTHERS  | 2021-10-25 |   Textbook    |-$15.00 |       |
Expense |   FOOD   | 2021-04-20 | Cheese Burger |-$4.20  |       |
                                     Net Total: |-$55.20
Here is the list of recurring entries added to the above list:
Expense |  OTHERS  | 2021-10-28 |    Netflix    |-$40.00 | MONTH | Forever :D
Expense |  OTHERS  | 2021-10-28 |      Viu      |-$30.00 | MONTH | Forever :D
```

```
view income
Here is the list of your entries:
  Type  | Category |    Date    | Name  | Amount | Every |   Until
Income  |  WAGES   | 2021-11-03 | Sales | $34.00 |       |
                             Net Total: | $34.00
Here is the list of recurring entries added to the above list:
```

```
view month 4 year 2021
For the year 2021:
For the month of APRIL:
Here is the list of your entries:
  Type  | Category |    Date    |     Name      | Amount | Every |   Until
Expense |   FOOD   | 2021-04-20 | Cheese Burger |-$4.20  |       |
                                     Net Total: |-$4.20
Here is the list of recurring entries added to the above list:
```

```
view from 2021-03-25 2022-01-02 by amount ascending
Here is the list of your entries:
Since 2021-03-25 to 2022-01-02:
  Type  | Category |    Date    |     Name      | Amount | Every |   Until
Expense |   FOOD   | 2021-04-20 | Cheese Burger |-$4.20  |       |
Expense |  OTHERS  | 2021-10-25 |   Textbook    |-$15.00 |       |
Income  |  WAGES   | 2021-11-03 |     Sales     | $34.00 |       |
                                     Net Total: | $14.80
Here is the list of recurring entries added to the above list:
```

## <a name="delete"></a>Deleting an entry: `delete`

Deletes an existing entry.

Format: `delete [n/NAME] [d/DATE] [a/AMOUNT] [c/CATEGORY_NUMBER]`

- At least one of the optional fields must be provided.
- Our program searches the entry that matches the fields provided by the user.
    - If there is more than 1 `Expense` or `Income` matching the query, the program will return a list for the user to
      choose from. The user would then have to confirm the deletion of the entry.
    - If there is 1  `Expense` or `Income` matching the query, the program will prompt the user to confirm the deletion
      of that  `Expense` or `Income` .
- Deletes an entry of the specified `NAME`, `DATE`, `AMOUNT`, or `CATEGORY_NUMBER`
- `NAME` can be any string of characters
- `DATE` can be any of the [acceptable date formats](#dateFormat).
- `AMOUNT` is in dollars. Numbers after the decimal point are in cents. Eg. 4.5 is $4.50
- `CATEGORY_NUMBER` is any integer from 0 to 7. Please refer to the [available categories](#categoryList).

Examples:

- `delete n/Textbook d/2012-09-21 a/15`
- `delete n/Cheese Burger d/2020-04-20 a/4.2`

Examples and expected output:

- If user query only matches 1 `Expense` or `Income` in the expense list

```
delete n/Textbook d/2012-09-21
Is this what you want to delete?
    Expense  | OTHERS | 2012-09-21 | Textbook | $40.00
Type "y" if yes. Type "n" if not.
y
I have deleted: Expense  | OTHERS | 2012-09-21 | Textbook | $40.00
```

- If user query matches more than 1 `Expense` or `Income` in the list

```
delete n/Cheese Burger d/2020-04-20 a/4.2
Here is the list of items containing the keyword.
 Index |   Type  | Category |    Date    |     Name      | Amount | Every |   Until
   1   | Income  |  OTHERS  | 2020-04-20 | Cheese Burger |-$4.20  
   2   | Expense |  OTHERS  | 2020-04-20 | Cheese Burger |-$4.20  
Enter the index of the item you want to delete. To cancel, type "cancel"
1
I have deleted: Income  | OTHERS | 2020-04-20 | Cheese Burger | $4.20
```

## <a name="deleteR"></a>Deleting a recurring entry: `deleteR`

Deletes an existing recurring entry.

Format: `deleteR [n/NAME] [d/DATE] [a/AMOUNT] [c/CATEGORY_NUMBER] [i/INTERVAL] [e/END_DATE]`

- At least one of the optional fields must be provided.
- Our program searches the entry that matches the fields provided by the user.
    - If there is more than 1 `RecurringExpense` or `RecurringIncome` matching the query,the program will return a list
      for the user to choose from. The user would then have to confirm the deletion of the entry.
    - If there is 1  `RecurringExpense` or `RecurringIncome` matching the query, the program will prompt the user to
      confirm the deletion of that  `Expense` or `Income` .
- Deletes an entry of the specified `NAME`, `DATE`, `AMOUNT`, or `CATEGORY_NUMBER`
- `NAME` can be any string of characters
- `DATE` can be any of the [acceptable date formats](#dateFormat).
- `AMOUNT` is in dollars. Numbers after the decimal point are in cents. Eg. 4.5 is $4.50
- `CATEGORY_NUMBER` is any integer from 0 to 7. Please refer to the [available categories](#categoryList).
- `INTERVAL` can be a string of either `MONTH` or `YEAR`. The string is not case-sensitive.
- `END_DATE` can be any of the [acceptable date formats](#dateFormat).

Examples:

- `deleteR n/Netflix`
- `deleteR i/mOnTh`

Examples and expected output:

- If user query only matches 1 `RecurringExpense` or `RecurringIncome` in the expense list

```
deleteR n/Netflix
Is this what you want to delete?
    Expense | OTHERS | 2021-10-28 | Netflix |-$90.00 | YEAR | Forever :D
Type "y" if yes. Type "n" if not.
y
I have deleted: Expense | OTHERS | 2021-10-28 | Netflix |-$90.00 | YEAR | Forever :D
```

- If user query matches more than 1 `RecurringExpense` or `RecurringIncome` in the list

```
deleteR i/mOnTh
Here is the list of items containing the keyword.
 Index |   Type  | Category |    Date    |  Name   | Amount | Every |   Until
   1   | Expense |  OTHERS  | 2021-10-28 | Netflix |-$40.00 | MONTH | Forever :D
   2   | Expense |  OTHERS  | 2021-10-28 |   Viu   |-$30.00 | MONTH | Forever :D
Enter the index of the item you want to delete. To cancel, type "cancel"
1
I have deleted: Expense | OTHERS | 2021-10-28 | Netflix |-$40.00 | MONTH | Forever :D
```

## <a name="delete"></a>Deleting all entries: `deleteAll`

Deletes all existing entries.

Format: `deleteAll [normal] [recurring]`

- Deletes all normal and recurring entries in the list.
- `[normal](optional)` and `[recurring](optional)` if appended, only deletes all entries of the corresponding type.
- If no modifiers are specified, it defaults to deleting all entries regardless of type.
- `normal` and `recurring` can be substituted for `n` and `r` respectively as a shortcut.
- `deleteall` also accepted as a command.

Examples:

- `deleteAll`
- `deleteall normal`

Examples and expected output:

```
deleteAll
Are you sure you want to delete all entries?
Type "y" if yes. Type "n" if not.
y
All entries successfully deleted.
```

```
deleteall normal
Are you sure you want to delete all entries?
Type "y" if yes. Type "n" if not.
y
All entries successfully deleted.
```

## <a name="edit"></a>Editing an entry: `edit`

Edits an existing entry

Format: `edit [n/NAME] [a/AMOUNT] [d/DATE] [c/CATEGORY_NUMBER]`

- At least one of the optional fields must be provided
- When editing fields, existing fields of the `Expense` or `Income` indicated by the user will be replaced.
- Our program searches the entry that matches the fields provided by the user.
    - If there is 1 `Expense` or `Income` matching the query, the program will prompt the user to confirm if they wish
      to edit that entry.
    - If there is more than 1 `Expense` or `Income` matching the query, the program will return a list for the user to
      choose from. The user would then have to confirm if they wish to edit the entry.
- `NAME` can be any string of characters
- `DATE` can be any of the [acceptable date formats](#dateFormat).
- `AMOUNT` is in dollars. Numbers after the decimal point are in cents. Eg. 4.5 is $4.50
- `CATEGORY_NUMBER` is any integer from 0 to 7. Please refer to the [available categories](#categoryList).

Examples:

- `edit n/Textbook d/2012-09-21 a/15`
- `edit n/Cheese Burger d/2020-04-20 a/4.2`

Examples and expected output:

- If user query only matches 1 `Expense` or `Income` in the expense list

```
edit n/Textbook d/2012-09-21 a/15
Is this what you want to edit?
    Expense  | OTHERS | 2012-09-21 | Textbook | $15.00
Type "y" if yes. Type "n" if not.
y
What would you like to edit?
a/14
Got it! I will update the fields accordingly!
```

- If user query matches more than 1 `Expense` or `Income` in the list

```
edit n/Cheese Burger d/2020-04-20 a/4.2
Here is the list of items containing the keyword.
 Index |   Type  | Category |    Date    |     Name      | Amount | Every |   Until
   1   | Expense |  OTHERS  | 2020-04-20 | Cheese Burger |-$4.20  
   2   | Expense |  OTHERS  | 2020-04-20 | Cheese Burger |-$4.20  
Enter the index of the item you want to edit. To cancel, type "cancel"
1
What would you like to edit?
c/7
Got it! I will update the fields accordingly!
```

## <a name="editR"></a>Editing a recurring entry: `editR`

Edits an existing recurring entry

Format: `editR [n/NAME] [d/DATE] [a/AMOUNT] [c/CATEGORY_NUMBER] [i/INTERVAL] [e/END_DATE]`

- At least one of the optional fields must be provided
- When editing fields, existing fields of the `Expense` or `Income` indicated by the user will be replaced.
- Our program searches the entry that matches the fields provided by the user.
    - If there is 1 `Expense` or `Income` matching the query, the program will prompt the user to confirm if they wish
      to edit that entry.
    - If there is more than 1 `Expense` or `Income` matching the query, the program will return a list for the user to
      choose from. The user would then have to confirm if they wish to edit the entry.
- `NAME` can be any string of characters
- `DATE` can be any of the [acceptable date formats](#dateFormat).
- `AMOUNT` is in dollars. Numbers after the decimal point are in cents. Eg. 4.5 is $4.50
- `CATEGORY_NUMBER` is any integer from 0 to 7. Please refer to the [available categories](#categoryList).
- `INTERVAL` can be a string of either `MONTH` or `YEAR`. The string is not case-sensitive.
- `END_DATE` can be any of the [acceptable date formats](#dateFormat).

Examples:

- `editR n/Textbook d/2012-09-21 a/15`
- `editR n/Cheese Burger d/2020-04-20 a/4.2`

Examples and expected output:

- If user query only matches 1 `Expense` or `Income` in the expense list

```
editR n/Textbook d/2012-09-21 a/15
Is this what you want to edit?
    Expense | OTHERS | 2012-09-21 | Textbook |-$15.00 | MONTH | Forever :D
Type "y" if yes. Type "n" if not.
y
What would you like to edit?
n/NETFLIX
Got it! I will update the fields accordingly!
```

- If user query matches more than 1 `Expense` or `Income` in the list

```
editR n/Textbook d/2012-09-21 a/15
Here is the list of items containing the keyword.
 Index |   Type  | Category |    Date    |   Name   | Amount | Every |   Until
   1   | Expense |  OTHERS  | 2012-09-21 | Textbook |-$15.00 | MONTH | Forever :D
   2   | Expense |  OTHERS  | 2012-09-21 | Textbook |-$15.00 | MONTH | Forever :D
Enter the index of the item you want to edit. To cancel, type "cancel"
1
What would you like to edit?
a/5
Got it! I will update the fields accordingly!
```

## <a name="cat"></a>View available categories: `cat`

Shows a list of all available categories and its corresponding tag number.

Format: `cat`

Expected Output:

```
Here are the categories and its tag number
Expenses           | Income
c/0 FOOD           | c/0 ALLOWANCE
c/1 ENTERTAINMENT  | c/1 WAGES
c/2 TRANSPORTATION | c/2 SALARY
c/3 HOUSEHOLD      | c/3 INTERESTED
c/4 APPAREL        | c/4 INVESTMENT
c/5 BEAUTY         | c/5 COMMISSION
c/6 GIFT           | c/6 GIFT
c/7 OTHERS         | c/7 OTHERS
```

## <a name="set"></a>Setting budget: `set`

Set budget for individual categories>

Format: `set c/CATEGORY_NUMBER a/AMOUNT`

- `set` takes in 2 mandatory fields, `c/CATEGORY_NUMBER` and `a/AMOUNT`.
- `AMOUNT` is in dollars. Numbers after the decimal point are in cents. Eg. 4.5 is $4.50.
- `CATEGORY_NUMBER` is any integer from 0 to 7. Please refer to the [available categories](#categoryList).

Example: If you want to set budget for "FOOD" to $100, type `set c/0 a/100`, as `c/0` correspond to "FOOD".

Expected Output:

```
Budget for FOOD set to $100.00
```

## <a name="budget"></a>View monthly budget: `budget`

View monthly spending and budget for current month

Format: `budget`

Expected Output:

```
Here is the budget for NOVEMBER 2021
   Category    | Amount | Budget  | Percentage
     FOOD      |  $5.00 / $100.00 | 5.00%
ENTERTAINMENT  |  $0.00 / Not set | 
TRANSPORTATION |  $0.00 / Not set | 
  HOUSEHOLD    |  $0.00 / Not set | 
   APPAREL     |  $0.00 / Not set | 
    BEAUTY     |  $0.00 / Not set | 
     GIFT      |  $0.00 / Not set | 
    OTHERS     | $23.50 / Not set | 
```

## <a name="exit"></a>Exit the program: `exit`

Exits the Mint program.

Format: `exit`

Expected Output:

- `Bye! Thanks for using Mint. See you soon :D`

## <a name="Commannd Summary"></a>Command Summary

Please refresh page if table is not rendered properly.

| Command | Format,Examples |
| -----| -----|
|add |`add` `[income]` `n/NAME` `a/AMOUNT` `[d/DATE]` `[c/CATEGORY_NUMBER]` <br> e.g. `add n/burger a/5 d/2021-10-20 c/0` | 
|addR | `addR` `[income]` `n/NAME a/AMOUNT` `[d/DATE]` `[c/CATEGORY_NUMBER]` `i/INTERVAL` `[e/END_DATE]` <br> e.g. `addR a/90 d/2021-12-03 n/phone bills c/3 i/MONTH` |
|edit |`edit` `[n/NAME]` `[a/AMOUNT]` `[d/DATE]` `[c/CATEGORY_NUMBER]`<br>  e.g. `edit n/burger a/5 d/2021-10-20 c/0` | 
|editR |`editR` `[n/NAME]` `[d/DATE]` `[a/AMOUNT]` `[c/CATEGORY_NUMBER]` `[i/INTERVAL]` `[e/END_DATE]` <br> e.g.`editR n/Textbook d/2012-09-21 a/15` | 
|delete |`delete` `[n/NAME]` `[a/AMOUNT]` `[d/DATE]` `[c/CATEGORY_NUMBER]` <br> e.g. `delete n/Cheese Burger d/20-10-2021 a/4.2` | 
|deleteR |`deleteR [n/NAME] [d/DATE] [a/AMOUNT] [c/CATEGORY_NUMBER] [i/INTERVAL] [e/END_DATE]`<br>  e.g. `deleteR n/Netflix` | 
|view |`view` `[income]` `[expense]` `[by SORTTYPE]` `[month MONTH]` `[year YEAR]` `[from STARTDATE [ENDDATE]]` `[up/ascending]` <br> e.g. `view from 2021-03-25 2022-01-02 by amount ascending`|
|view categories | `cat` | 
|set | `set` `c/CATEGORY_NUMBER` `a/AMOUNT`| 
|budget | `budget` |
|help | `help` | 
|exit | `exit` |

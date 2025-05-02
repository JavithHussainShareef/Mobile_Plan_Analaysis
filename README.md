# Mobile Plan Analyser

## Problem Statement
In Canada, selecting a suitable mobile plan is challenging due to the wide variety of plans offered by multiple providers, each with different pricing structures, data limits, and add-on features. Manually comparing plans across different provider websites is time-consuming and often confusing for users.

## About This Project

### Project Domain
Telecommunication / Web Automation

### Objective
The main objective of this project is to simplify the process of selecting a mobile plan by automating the comparison of available plans across major Canadian telecom providers. Using web scraping and automation, this tool recommends the most appropriate mobile plan based on the user's preferred provider and specific requirements.

## Features
- **Provider Selection**: Users can select their desired telecom provider (e.g., Bell, Rogers, Telus, etc.).
- **Automated Scraping**: Extracts up-to-date mobile plan details like price, data allowance, and offers from provider websites using Selenium.
- **Plan Recommendation**: Based on the selected provider, the system filters and recommends the best available plan.
- **User Interface (CLI/GUI)**: Simple interface for input and results (CLI version implemented, extendable to GUI).

## Tools Used
- **Java**: Core programming language used for the backend logic and automation workflows.
- **Selenium WebDriver**: For automating the process of navigating and scraping dynamic website content.
- **Maven**: Project build and dependency management tool.
- **ChromeDriver**: WebDriver implementation for running Selenium scripts on the Chrome browser.
- **JSoup (optional)**: For parsing static HTML content when applicable.
- **JUnit**: For writing and executing test cases to ensure scraping logic is working as expected.

## Getting Started

To get a local copy of the project up and running, follow these steps:

```bash
git clone https://github.com/JavithHussainShareef/Mobile_Plan_Analaysis.git

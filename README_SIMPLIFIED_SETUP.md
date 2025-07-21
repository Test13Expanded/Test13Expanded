# MotorPH Payroll System - Simplified Setup Guide

## 🚀 Quick Start (No Complex Dependencies)

This guide helps you run the MotorPH Payroll System without complex JUnit dependencies that cause compilation errors.

### Prerequisites
1. **Java Development Kit (JDK) 8 or higher**
2. **MySQL Server 8.0+**
3. **MySQL Workbench** (recommended)

### Required JAR Files (Minimal Set)
Only add these essential JAR files to your `lib` folder:

1. **mysql-connector-j-8.4.0.jar** - MySQL JDBC Driver ✅ (Already included)
   - This is the ONLY required JAR for basic functionality

### Optional JAR Files (For Advanced Features)
2. **jasperreports-6.20.6.jar** - For PDF generation ✅ (Already included)
3. **commons-collections4-4.4.jar** - For JasperReports ✅ (Already included)
4. **commons-logging-1.2.jar** - For JasperReports ✅ (Already included)

### Database Setup

1. **Run the SQL Setup Script**
   ```
   File: src/util/aoopdatabase_payroll.sql
   ```

2. **Configure MySQL Connection**
   - **Host**: localhost
   - **Port**: 3306
   - **Username**: root
   - **Password**: admin
   - **Database**: aoopdatabase_payroll

3. **Execute Setup Script**
   - Open MySQL Workbench
   - Connect to your MySQL server
   - Open and execute `src/util/aoopdatabase_payroll.sql`

### Running the Application

#### Method 1: Using Ant (Recommended)
```bash
# Navigate to project directory
cd "C:\Users\user\Desktop\Update AOOP\Test12Expanded-main"

# Clean and build
ant clean
ant compile

# Run the application
ant run
```

#### Method 2: Using Command Line
```bash
# Compile (from project root)
javac -cp "lib/*" -d build/classes src/**/*.java

# Run
java -cp "build/classes;lib/*" ui.MainApplication
```

#### Method 3: Using NetBeans
1. Open the project in NetBeans
2. Right-click project → Properties → Libraries
3. Add JAR files from `lib` folder
4. Run the project (F6)

### Default Login Credentials

- **Employee IDs**: 10001 to 10034
- **Default Password**: `password1234`

### Sample Login Accounts
- **Employee ID**: 10001 (CEO) - Password: `password1234`
- **Employee ID**: 10006 (HR Manager) - Password: `password1234`

### Running Tests (Simplified)

The project now uses simplified tests without JUnit dependencies:

```bash
# Run all tests
java -cp "build/classes;lib/*" Test.AllTestsRunner

# Run specific test
java -cp "build/classes;lib/*" Test.EmployeeModelTest
```

### Project Structure (Core Components)

```
src/
├── dao/           # Database Access Objects
├── model/         # Entity classes (Employee, Payroll, etc.)
├── service/       # Business logic (PayrollCalculator, etc.)
├── ui/            # User interface classes
├── view/          # Dashboard views
├── util/          # Utility classes (DBConnection, etc.)
└── Test/          # Test classes (simplified, no JUnit)
```

### Key Features Working

✅ **Database Integration**: MySQL with proper connection handling
✅ **Employee Management**: CRUD operations for employees
✅ **Payroll Processing**: Salary calculations with government contributions
✅ **Attendance Tracking**: Time in/out with calculations
✅ **Leave Management**: Leave request system
✅ **Role-Based Dashboards**: Different interfaces for different roles
✅ **Report Generation**: Basic reporting with JasperReports support
✅ **Testing**: Simplified test suite without external dependencies

### Troubleshooting

#### Common Issues

1. **Compilation Errors**
   - Make sure only `mysql-connector-j-8.4.0.jar` is in classpath for basic functionality
   - Remove or ignore JUnit-related JARs if causing issues

2. **Database Connection Failed**
   - Check MySQL server is running
   - Verify credentials in `src/util/DBConnection.java`
   - Ensure database `aoopdatabase_payroll` exists

3. **Class Not Found Errors**
   - Ensure all required JAR files are in `lib` folder
   - Check classpath includes both `build/classes` and `lib/*`

### What's Different from Complex Setup

❌ **Removed**: Complex JUnit 5 dependencies that cause module issues
❌ **Removed**: Mockito dependencies (not essential for basic functionality)
✅ **Kept**: Core business logic and database functionality
✅ **Kept**: All UI components and dashboards
✅ **Kept**: JasperReports for PDF generation (optional)

### Next Steps

1. **Test Database Connection**: Run `src/Test.java` to verify database setup
2. **Start Application**: Use `ui.MainApplication` as main class
3. **Login**: Use employee ID 10001 with password `password1234`
4. **Explore Features**: Try different dashboards based on employee roles

## 🎉 Success Criteria

- ✅ Application compiles without errors
- ✅ Database connection works
- ✅ Login system functions
- ✅ Basic payroll calculations work
- ✅ Employee management works
- ✅ Tests run without JUnit dependencies

This simplified setup focuses on core functionality without complex testing frameworks that can cause compilation issues.
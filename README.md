# Hospital simulator coding challenge

You were asked by a doctor to prepare a Hospital Simulator, which can simulate the future patients’ health state, based on their current health state and a list of drugs they are administered.  

Patients can have one of these states:

`F` Fever  
`H` Healthy  
`D` Diabetes  
`T` Tuberculosis  
`X` Dead

In the Hospital Simulator drugs are provided to all patients. It is not possible to target a specific patient. This is the list of available drugs:

`As` Aspirin  
`An` Antibiotic  
`I` Insulin  
`P` Paracetamol  

Drugs can change patients’ states. They can cure, cause side effects or even kill a patient if not properly prescribed.
Drug effects are described by the following rules:  

* **Aspirin** cures **Fever**  
* **Antibiotic** cures **Tuberculosis**  
* **Insulin prevents diabetic subjects from dying**, does not cure Diabetes  
* **Insulin** mixed with **Antibiotic** causes **Healthy** people to catch **Fever**  
* **Paracetamo**l cures **Fever**  
* **Paracetamol** kills subjects if mixed with **Aspirin**  
* **One time in a million** the Flying Spaghetti Monster shows his noodly power and
resurrects a **Dead** patient, the patient becomes **Healthy**  

## How to build it

To build this application, you will need at least a `JDK 11` installed in your operating system. You can use the provided maven wrapper or your own maven version if you already have installed it in your current operating system.  

`./mvnw clean install` (Mac & Linux) or `mvnw.cmd clean install` (Windows)

The result of the build will produce an executable jar containing all the project dependencies.

## How to run it

`java -jar hospital-simulator-1.0.jar [-hV] <patients>[,<patients>...] [<drugs>[,<drugs>...]]`

> picocli describes several ways to package an application for a more user-friendly experience using: *Launcher Script, GraalVM, etc...* More infos can be found [here](https://picocli.info/#_packaging_your_application).  

Even if this step is optional, we can just create an alias on unix-based operating systems:

`alias HospitalSimulator='java -jar hospital-simulator-1.0.jar'`

Now we can run the application using:

`HospitalSimulator [-hV] <patients>[,<patients>...] [<drugs>[,<drugs>...]]`

Note: Help and version can be displayed using options `-h` and `-V`.  

### Parameter 1

List of patients’ health state codes, separated by a comma, e.g. `D,F,F` means we have 3 patients, one with *Diabetes* and two with *Fever*.

### Parameter 2

List of drug codes, separated by a comma, e.g. `As,I` means patients will be treated with *Aspirin* and *Insulin*.


## Project dependencies

| Name                                                                   | Description                                                                                        |
|------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| [picocli](https://picocli.info "a mighty tiny command line interface") | Picocli is a one-file framework for creating Java command line applications with almost zero code. |
| [easy-rules](https://github.com/j-easy/easy-rules/wiki)                | Easy Rules is a Java rules engine with the ability to define rules using an Expression Language    |
| [logback](https://logback.qos.ch)                                                            | Logback is intended as a successor to the popular log4j project.                                   |

## Remarks

The order in which the rules are executed is important in order to explain the results. Currently, we follow the order defined in the problem statement. For example, `I+An` gives `Fever` to `Healthy` people but then with `P` people will become `Healthy` again.   

`HospitalSimulator H I,An,P` will produce `F:0,H:1,D:0,T:0,X:0`  

but if we replace `P` by `As` which also cures `Fever` 

`HospitalSimulator H I,An,As` will produce `F:1,H:0,D:0,T:0,X:0`  


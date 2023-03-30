import { useEffect, useState } from "react";
import {
  Box,
  FormControl,
  FormLabel,
  Input,
  Stack,
  Button,
  useColorModeValue,
  FormHelperText,
  FormErrorMessage,
  InputGroup,
  InputRightElement,
} from "@chakra-ui/react";
import { ViewIcon, ViewOffIcon } from "@chakra-ui/icons";
import { Field, Form, Formik } from "formik";
import fetchWrapper, { getAuthorities } from "../utils/fetch_wrapper";
import notification from "../utils/notification";

export default function Registration() {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [disableForm, setDisableForm] = useState(false);

  // User and authentication info
  const [GrantedAuth, setGrantedAuth] = useState([]);
  useEffect(() => {
    setGrantedAuth(getAuthorities());
  }, []);

  // Function to validate the firstname field
  const validateFirstname = (value) => {
    let error;
    if (!value) {
      error =
        "Please enter a valid first name. First name cannot be left empty";
    }
    return error;
  };

  // Function to validate the lastname field
  const validateLastname = (value) => {
    let error;
    if (!value) {
      error = "Please enter a valid last name. Last name cannot be left empty";
    }
    return error;
  };

  // Function to validate the email field
  const validateEmail = (value) => {
    let error;
    if (!value) {
      error = "Please enter a valid email. Email cannot be left empty";
    } else if (
      !/^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/.test(value)
    ) {
      error =
        "Please enter a valid email. The email address you entered is not valid";
    }
    return error;
  };

  // Function to validate the password field
  const validatePassword = (value) => {
    let error;
    if (!value) {
      error = "Please enter a valid password. Password cannot be left empty";
    } else if (
      value.length < 8 ||
      !/.*[0-9].*/.test(value) ||
      !/.*[a-z].*/.test(value) ||
      !/.*[A-Z].*/.test(value)
    ) {
      error =
        "Please enter a valid password. Passwords must be at least 8 characters long and contain at least one number, one lowercase character and one uppercase character";
    }
    return error;
  };

  // Function to validate the confirm password field
  const validateConfirmPassword = (value, password) => {
    let error;
    if (value !== password) {
      error = "Passwords do not match.";
    }
    return error;
  };

  // Function to handle the registration form submission
  async function handleRegistrationForm(values, actions) {
    const payload = {
      email: values.email,
      password: values.password,
      firstname: values.firstname,
      lastname: values.lastname,
    };
    let response = null;

    if (
      GrantedAuth.length === 0 &&
      !window.location.pathname.includes("moderator")
    ) {
      // User is not authenticated and is registering as a regular user
      response = await fetchWrapper(
        "/api/appuser/regular",
        null,
        "POST",
        payload
      );
    } else if (
      GrantedAuth.includes("Owner") &&
      window.location.pathname.includes("moderator")
    ) {
      // User is an admin and is registering a moderator
      response = await fetchWrapper(
        "/api/appuser/moderator",
        null,
        "POST",
        payload
      );
    } else {
      // User does not have the required permissions
      notification(
        "error",
        "You do not have the required permissions to perform this action.",
        null
      );
      setDisableForm(true);
      actions.setSubmitting(false);
      return;
    }

    if (
      response.ok &&
      GrantedAuth.includes("Owner") &&
      window.location.pathname.includes("moderator")
    ) {
      // User is an admin and successfully registered a moderator
      notification("success", "Moderator account created successfully.", null);
      actions.setErrors({
        email: "This email is now registered as a moderator account",
      });
      actions.setSubmitting(false);
    } else if (response.ok) {
      // User successfully registered as a regular user
      actions.setSubmitting(false);
      window.location.href = "/login"; // Redirect to login page
    } else if (response !== null) {
      // User registration failed display error mesages
      notification("error", "An error occurred.", response.errorMessages);
      actions.setErrors({
        firstname: response.errorMessages.toLowerCase().includes("first name")
          ? response.errorMessages
          : null,
        lastname: response.errorMessages.toLowerCase().includes("last name")
          ? response.errorMessages
          : null,
        email: response.errorMessages.toLowerCase().includes("email")
          ? response.errorMessages
          : null,
        password: response.errorMessages.toLowerCase().includes("password")
          ? response.errorMessages
          : null,
      });
      actions.setSubmitting(false);
    }
  }

  return (
    <Stack spacing={8} mx={"auto"} maxW={"2xl"} py={12} px={6}>
      <Box
        rounded={"lg"}
        bg={useColorModeValue("white", "gray.700")}
        boxShadow={"lg"}
        p={8}
      >
        <Formik
          initialValues={{
            firstname: "",
            lastname: "",
            email: "",
            password: "",
            confirmPassword: "",
          }}
          onSubmit={async (values, actions) => {
            await handleRegistrationForm(values, actions);
          }}
        >
          {(props) => (
            <Form>
              <Stack spacing={4}>
                <Box>
                  <Field
                    name="firstname"
                    validate={(value) => validateFirstname(value)}
                  >
                    {({ field, form }) => (
                      <FormControl
                        isInvalid={
                          form.errors.firstname && form.touched.firstname
                        }
                      >
                        <FormLabel>First Name</FormLabel>
                        <Input {...field} type="text" />
                        <FormErrorMessage>
                          {form.errors.firstname}
                        </FormErrorMessage>
                      </FormControl>
                    )}
                  </Field>
                </Box>
                <Box>
                  <Field
                    name="lastname"
                    validate={(value) => validateLastname(value)}
                  >
                    {({ field, form }) => (
                      <FormControl
                        id="lastName"
                        isInvalid={
                          form.errors.lastname && form.touched.lastname
                        }
                      >
                        <FormLabel>Last Name</FormLabel>
                        <Input {...field} type="text" />
                        <FormErrorMessage>
                          {form.errors.lastname}
                        </FormErrorMessage>
                      </FormControl>
                    )}
                  </Field>
                </Box>
                <Box>
                  <Field
                    name="email"
                    validate={(value) => validateEmail(value)}
                  >
                    {({ field, form }) => (
                      <FormControl
                        id="email"
                        isInvalid={form.errors.email && form.touched.email}
                        borderColor={
                          form.errors.email ==
                          "This email is now registered as a moderator account"
                            ? "green.500"
                            : "inherit"
                        }
                      >
                        <FormLabel>Username</FormLabel>
                        <Input
                          {...field}
                          type="email"
                          placeholder="email@example.com"
                          style={
                            form.errors.email ==
                            "This email is now registered as a moderator account"
                              ? {
                                  borderColor: "green",
                                  boxShadow: "none",
                                }
                              : null
                          }
                        />
                        <FormErrorMessage
                          color={
                            form.errors.email ==
                            "This email is now registered as a moderator account"
                              ? "green.500"
                              : "red.500"
                          }
                        >
                          {form.errors.email}
                        </FormErrorMessage>
                      </FormControl>
                    )}
                  </Field>
                </Box>
                <Stack>
                  <Box>
                    <Field
                      name="password"
                      validate={(value) => validatePassword(value)}
                    >
                      {({ field, form }) => (
                        <FormControl
                          id="password"
                          isInvalid={
                            form.errors.password && form.touched.password
                          }
                        >
                          <FormLabel>Password</FormLabel>
                          <FormHelperText mb={4}>
                            Passwords must be at least 8 characters long and
                            contain at least one number, one lowercase character
                            and one uppercase character.
                          </FormHelperText>
                          <InputGroup>
                            <Input
                              {...field}
                              type={showPassword ? "text" : "password"}
                              placeholder="Password"
                            />
                            <InputRightElement h={"full"}>
                              <Button
                                variant={"ghost"}
                                onClick={() =>
                                  setShowPassword(
                                    (showPassword) => !showPassword
                                  )
                                }
                              >
                                {showPassword ? <ViewIcon /> : <ViewOffIcon />}
                              </Button>
                            </InputRightElement>
                          </InputGroup>
                          <FormErrorMessage>
                            {form.errors.password}
                          </FormErrorMessage>
                        </FormControl>
                      )}
                    </Field>
                  </Box>
                  <Box>
                    <Field
                      name="confirmPassword"
                      validate={(value) =>
                        validateConfirmPassword(value, props.values.password)
                      }
                    >
                      {({ field, form }) => (
                        <FormControl
                          id="confirmPassword"
                          isInvalid={
                            form.errors.confirmPassword &&
                            form.touched.confirmPassword
                          }
                        >
                          <InputGroup>
                            <Input
                              {...field}
                              type={showConfirmPassword ? "text" : "password"}
                              placeholder="Confirm password"
                            />
                            <InputRightElement h={"full"}>
                              <Button
                                variant={"ghost"}
                                onClick={() =>
                                  setShowConfirmPassword(
                                    (showConfirmPassword) =>
                                      !showConfirmPassword
                                  )
                                }
                              >
                                {showConfirmPassword ? (
                                  <ViewIcon />
                                ) : (
                                  <ViewOffIcon />
                                )}
                              </Button>
                            </InputRightElement>
                          </InputGroup>
                          <FormErrorMessage>
                            {form.errors.confirmPassword}
                          </FormErrorMessage>
                        </FormControl>
                      )}
                    </Field>
                  </Box>
                </Stack>
                <Stack align={"center"}>
                  <Button
                    size="lg"
                    bg={"blue.400"}
                    color={"white"}
                    _hover={{
                      bg: "blue.500",
                    }}
                    w={"50%"}
                    loadingText="Submitting"
                    isLoading={props.isSubmitting}
                    isDisabled={
                      !props.isValid ||
                      props.isSubmitting ||
                      disableForm ||
                      !props.dirty
                    }
                    type="submit"
                  >
                    Register
                  </Button>
                </Stack>
              </Stack>
            </Form>
          )}
        </Formik>
      </Box>
    </Stack>
  );
}

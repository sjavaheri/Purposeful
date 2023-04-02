// Copied from: https://chakra-templates.dev/forms/authentication
import {
  Box,
  FormControl,
  FormLabel,
  Input,
  InputGroup,
  Stack,
  Button,
  InputRightElement,
  useColorModeValue,
  FormErrorMessage,
} from "@chakra-ui/react";
import { useState } from "react";
import { login, verifyToken } from "../utils/fetch_wrapper";
import notification from "../utils/notification";
import { ViewIcon, ViewOffIcon } from "@chakra-ui/icons";
import { Field, Form, Formik } from "formik";

export default function Login() {
  const [showPassword, setShowPassword] = useState(false);

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
    // Check if the password field is empty
    if (!value || value.trim() === "") {
      error = "Please enter a valid password. Password cannot be left empty";
    }
    return error;
  };

  const handleSubmit = async (values, actions) => {
    event.preventDefault();
    let success = await login(values.email, values.password);
    if (success && (await verifyToken())) {
      // User successfully logged in as a regular user
      actions.setSubmitting(false);
      window.location.href = "/"; // Redirect to home page
    }
    if (!success) {
      // User login failed display error mesages
      notification("error", "Invalid email and/or password.", null);
      actions.setSubmitting(false);
      return;
    }
  };

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
            email: "",
            password: "",
          }}
          onSubmit={async (values, actions) => {
            await handleSubmit(values, actions);
          }}
        >
          {(props) => (
            <Form>
              <Stack spacing={4}>
                <Box>
                  <Field
                    name="email"
                    validate={(value) => validateEmail(value)}
                  >
                    {({ field, form }) => (
                      <FormControl
                        id="email"
                        isInvalid={form.errors.email && form.touched.email}
                      >
                        <FormLabel>Username</FormLabel>
                        <Input
                          {...field}
                          type="email"
                          placeholder="email@example.com"
                        />
                        <FormErrorMessage>{form.errors.email}</FormErrorMessage>
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
                      !props.isValid || props.isSubmitting || !props.dirty
                    }
                    type="submit"
                  >
                    Login
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

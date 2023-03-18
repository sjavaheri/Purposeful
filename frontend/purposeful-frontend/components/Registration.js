import { useState } from "react";
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

export default function Registration() {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  // Function to validate the confirm password field
  const validateConfirmPassword = (value, password) => {
    let error;
    if (value !== password) {
      error = "Passwords do not match.";
    }
    return error;
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
            firstname: "",
            lastname: "",
            email: "",
            password: "",
            confirmPassword: "",
          }}
          onSubmit={(values, actions) => {
            setTimeout(async () => {
              console.log(values); // TODO: To be removed once the API is connected
              // TODO: Set the error messages for the fields according to the API response
              // TODO: Differentiate API methods depending on authentication status
              actions.setSubmitting(false);
              // TODO: Redirect to the login page
            }, 1000);
          }}
        >
          {(props) => (
            <Form>
              <Stack spacing={4}>
                <Box>
                  <Field name="firstname">
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
                  <Field name="lastname">
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
                  <Field name="email">
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
                    <Field name="password">
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

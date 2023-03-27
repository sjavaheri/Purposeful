// Copied from: https://chakra-templates.dev/forms/authentication
import {
  Flex,
  Box,
  FormControl,
  FormLabel,
  Input,
  Checkbox,
  Stack,
  Link,
  Button,
  Heading,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
import { useState } from "react";
import { login, verifyToken } from "../utils/fetch_wrapper";
import notification from "../utils/notification";

export default function Login() {

  // bring back to home page if user is already logged in (not working on runtime due to localStorage not existing at runtime)
  if (typeof window !== 'undefined' && localStorage.getItem("token")) {
    window.location.href = "/"; // Redirect to home page if user is already logged in
  }

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

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

  const handleSubmit = async (event) => {
    event.preventDefault();
    // alert(`Email: ${username} & Password: ${password}`);
    let success = await login(username, password);
    await verifyToken();
    if (success) {
      // User successfully logged in as a regular user
      window.location.href = "/"; // Redirect to home page
    }
    else {
      // User login failed display error mesages
      notification("error", "Invalid email and/or password.", null);
    }
    // logout();
    // console.log(verifyToken());
    // what to do if verify is false?
  };

  return (
    <Flex
      minH={"100vh"}
      align={"center"}
      justify={"center"}
      bg={useColorModeValue("gray.50", "gray.800")}>
      <Stack spacing={8} mx={"auto"} maxW={"lg"} py={12} px={6}>
        <Stack align={"center"}>
          <Heading fontSize={"4xl"}>Sign in to your account</Heading>
        </Stack>
        <Box
          rounded={"lg"}
          bg={useColorModeValue("white", "gray.700")}
          boxShadow={"lg"}
          p={8}>
          <Stack spacing={4}>
            <form onSubmit={handleSubmit}>
              <FormControl id="email">
                <FormLabel>Email address</FormLabel>
                <Input
                  type="email"
                  // value={username}
                  onChange={(event) => setUsername(event.currentTarget.value)}
                  name="email"
                  validate={(value) => validateEmail(value)}
                />
              </FormControl>
              <FormControl id="password">
                <FormLabel>Password</FormLabel>
                <Input
                  type="password"
                  // value={password}
                  onChange={(event) => setPassword(event.currentTarget.value)}
                />
              </FormControl>
              <Stack spacing={10}>
                <Stack
                  direction={{ base: "column", sm: "row" }}
                  align={"start"}
                  justify={"space-between"}>
                </Stack>
                <Button
                  bg={"blue.400"}
                  color={"white"}
                  _hover={{
                    bg: "blue.500",
                  }}
                  type="submit">
                  Sign in
                </Button>
              </Stack>
            </form>
          </Stack>
        </Box>
      </Stack>
    </Flex>
  );
}

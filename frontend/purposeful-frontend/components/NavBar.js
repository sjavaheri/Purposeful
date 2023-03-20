import {
  Avatar,
  Box,
  Button,
  Center,
  Flex,
  HStack,
  Link,
  Menu,
  MenuButton,
  MenuDivider,
  MenuItem,
  MenuList,
  Stack,
  Text,
  useColorMode,
  useColorModeValue,
  VStack,
} from "@chakra-ui/react";
import { MoonIcon, SunIcon } from "@chakra-ui/icons";
import { RiAccountCircleLine, RiLogoutBoxRLine } from "react-icons/ri";
import { FcIdea } from "react-icons/fc";

// TODO: Links might need to change
// TODO: Links might need to be dynamic based on user role
const Links = ["Home", "Ideas", "Domain"];

const NavLink = ({ children }) => (
  <Link
    px={2}
    py={1}
    rounded={"md"}
    _hover={{
      textDecoration: "none",
      bg: useColorModeValue("gray.200", "gray.700"),
    }}
    // TODO: Change this to the correct links
    href={children === "Home" ? "/" : `/${children.toLowerCase()}`}
  >
    {children}
  </Link>
);

export default function NavBar() {
  const { colorMode, toggleColorMode } = useColorMode();

  return (
    <>
      <Box bg={useColorModeValue("gray.100", "gray.900")} px={4}>
        <Flex h={24} alignItems={"center"} justifyContent={"space-between"}>
          <HStack spacing={8} alignItems={"center"}>
            <VStack
              as={"Button"}
              spacing={0.5}
              alignItems={"left"}
              onClick={() => {
                window.location.href = "/";
              }}
            >
              <Text as="em" color={"gray.500"}>
                Share.
              </Text>
              <Text as="em" color={"gray.500"}>
                Create.
              </Text>
              <Text as="em" color={"gray.500"}>
                Purposefully.
              </Text>
            </VStack>
            <HStack
              as={"nav"}
              spacing={4}
              display={{ base: "none", md: "flex" }}
            >
              {Links.map((link) => (
                <NavLink key={link}>{link}</NavLink>
              ))}
            </HStack>
          </HStack>

          <Flex alignItems={"center"}>
            <Stack direction={"row"} spacing={7}>
              <Button onClick={toggleColorMode}>
                {colorMode === "light" ? <MoonIcon /> : <SunIcon />}
              </Button>
              <Menu>
                <MenuButton
                  as={Button}
                  rounded={"full"}
                  variant={"link"}
                  cursor={"pointer"}
                  minW={0}
                >
                  <Avatar
                    size={"md"}
                    src={
                      // TODO: Replace with user's username for their own random avatar
                      "https://avatars.dicebear.com/api/big-smile/{username}.svg"
                    }
                  />
                </MenuButton>
                <MenuList alignItems={"center"}>
                  <br />
                  <Center>
                    <Avatar
                      size={"2xl"}
                      src={
                        // TODO: Replace with user's username for their own random avatar
                        "https://avatars.dicebear.com/api/big-smile/{username}.svg"
                      }
                    />
                  </Center>
                  <br />
                  <Center>
                    {/* TODO: Replace with user's name */}
                    <p>Name</p>
                  </Center>
                  <br />
                  <MenuDivider />
                  <MenuItem
                    onClick={() => {
                      window.location.href = "/myideas";
                    }}
                  >
                    <HStack spacing={4}>
                      <FcIdea />
                      <Text>My Ideas</Text>
                    </HStack>
                  </MenuItem>
                  <MenuItem
                    onClick={() => {
                      // TODO: Change this to the correct link
                      window.location.href = "/account";
                    }}
                  >
                    <HStack spacing={4}>
                      <RiAccountCircleLine />
                      <Text>Account</Text>
                    </HStack>
                  </MenuItem>
                  <MenuItem
                    onClick={() => {
                      // TODO: Add logout functionality
                    }}
                  >
                    <HStack spacing={4}>
                      <RiLogoutBoxRLine color={"red"} />
                      <Text color={"red.400"}>Logout</Text>
                    </HStack>
                  </MenuItem>
                </MenuList>
              </Menu>
            </Stack>
          </Flex>
        </Flex>
      </Box>
    </>
  );
}

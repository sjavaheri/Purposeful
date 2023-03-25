import { useEffect, useState } from "react";
import {
  Avatar,
  Box,
  Button,
  Center,
  Flex,
  HStack,
  Icon,
  Link,
  Menu,
  MenuButton,
  MenuDivider,
  MenuItem,
  MenuList,
  Popover,
  PopoverContent,
  PopoverTrigger,
  Stack,
  Text,
  Tooltip,
  useColorMode,
  useColorModeValue,
  VStack,
} from "@chakra-ui/react";
import { ChevronRightIcon, MoonIcon, SunIcon } from "@chakra-ui/icons";
import { RiAccountCircleLine, RiLogoutBoxRLine } from "react-icons/ri";
import { logout, verifyToken, getAuthorities } from "../utils/fetch_wrapper";

export default function NavBar() {
  // Colors and theme
  const { colorMode, toggleColorMode } = useColorMode();
  const bgColorBase = useColorModeValue("gray.100", "gray.900");
  const bgColorBaseHover = useColorModeValue("gray.200", "gray.800");
  const textColorBase = useColorModeValue("gray.600", "gray.200");
  const textColorBaseHover = useColorModeValue("gray.800", "gray.400");

  // User and authentication info
  const [appUser, setAppUser] = useState(null);
  const [GrantedAuth, setGrantedAuth] = useState([]);
  const [verified, setVerified] = useState(false);
  useEffect(() => {
    setAppUser(localStorage.getItem("appUser"));
    setGrantedAuth(getAuthorities());
    setVerified(verifyToken());
  }, []);

  return (
    <>
      <Box bg={bgColorBase} px={4}>
        <Flex h={24} alignItems={"center"} justifyContent={"space-between"}>
          <HStack spacing={8} alignItems={"center"}>
            <VStack
              as={"button"}
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
              <Link
                p={2}
                fontSize={"2xl"}
                fontWeight={350}
                color={textColorBase}
                _hover={{
                  textDecoration: "underline",
                  color: textColorBaseHover,
                }}
                onClick={() => {
                  window.location.href = "/";
                }}
              >
                Home
              </Link>
              <Box>
                <Popover trigger="hover" placement="bottom-start">
                  <PopoverTrigger>
                    <Text
                      p={2}
                      fontSize={"2xl"}
                      fontWeight={350}
                      color={textColorBase}
                      _hover={{
                        textDecoration: "underline",
                        color: textColorBaseHover,
                      }}
                    >
                      Ideas
                    </Text>
                  </PopoverTrigger>
                  <PopoverContent
                    border={0}
                    boxShadow={"xl"}
                    bg={bgColorBase}
                    p={4}
                    rounded={"xl"}
                    minW={"sm"}
                  >
                    <Stack>
                      <Link
                        href="/create"
                        role={"group"}
                        display={"block"}
                        p={2}
                        rounded={"md"}
                        _hover={{
                          bg: bgColorBaseHover,
                        }}
                      >
                        <Stack direction={"row"} align={"center"}>
                          <Box>
                            <Text
                              transition={"all .3s ease"}
                              _groupHover={{ color: "blue.500" }}
                              fontWeight={500}
                            >
                              Create Idea
                            </Text>
                            <Text fontSize={"sm"}>Have an idea? Share it!</Text>
                          </Box>
                          <Flex
                            transition={"all .3s ease"}
                            transform={"translateX(-10px)"}
                            opacity={0}
                            _groupHover={{
                              opacity: "100%",
                              transform: "translateX(0)",
                            }}
                            justify={"flex-end"}
                            align={"center"}
                            flex={1}
                          >
                            <Icon
                              color={"blue.500"}
                              w={5}
                              h={5}
                              as={ChevronRightIcon}
                            />
                          </Flex>
                        </Stack>
                      </Link>
                      <Link
                        href="/myideas"
                        role={"group"}
                        display={"block"}
                        p={2}
                        rounded={"md"}
                        _hover={{
                          bg: bgColorBaseHover,
                        }}
                      >
                        <Stack direction={"row"} align={"center"}>
                          <Box>
                            <Text
                              transition={"all .3s ease"}
                              _groupHover={{ color: "blue.500" }}
                              fontWeight={500}
                            >
                              My Ideas
                            </Text>
                            <Text fontSize={"sm"}>
                              View and edit your ideas.
                            </Text>
                          </Box>
                          <Flex
                            transition={"all .3s ease"}
                            transform={"translateX(-10px)"}
                            opacity={0}
                            _groupHover={{
                              opacity: "100%",
                              transform: "translateX(0)",
                            }}
                            justify={"flex-end"}
                            align={"center"}
                            flex={1}
                          >
                            <Icon
                              color={"blue.500"}
                              w={5}
                              h={5}
                              as={ChevronRightIcon}
                            />
                          </Flex>
                        </Stack>
                      </Link>
                      <Link
                        href="/myideasofinterest"
                        role={"group"}
                        display={"block"}
                        p={2}
                        rounded={"md"}
                        _hover={{
                          bg: bgColorBaseHover,
                        }}
                      >
                        <Stack direction={"row"} align={"center"}>
                          <Box>
                            <Text
                              transition={"all .3s ease"}
                              _groupHover={{ color: "blue.500" }}
                              fontWeight={500}
                            >
                              My Interests
                            </Text>
                            <Text fontSize={"sm"}>
                              Track ideas you&apos;re interested in.
                            </Text>
                          </Box>
                          <Flex
                            transition={"all .3s ease"}
                            transform={"translateX(-10px)"}
                            opacity={0}
                            _groupHover={{
                              opacity: "100%",
                              transform: "translateX(0)",
                            }}
                            justify={"flex-end"}
                            align={"center"}
                            flex={1}
                          >
                            <Icon
                              color={"blue.500"}
                              w={5}
                              h={5}
                              as={ChevronRightIcon}
                            />
                          </Flex>
                        </Stack>
                      </Link>
                    </Stack>
                  </PopoverContent>
                </Popover>
              </Box>
              <Box>
                <Popover trigger="hover" placement="bottom-start">
                  <PopoverTrigger>
                    <Text
                      p={2}
                      fontSize={"2xl"}
                      fontWeight={350}
                      color={textColorBase}
                      _hover={{
                        textDecoration: "underline",
                        color: textColorBaseHover,
                      }}
                    >
                      Domain
                    </Text>
                  </PopoverTrigger>
                  <PopoverContent
                    border={0}
                    boxShadow={"xl"}
                    bg={bgColorBase}
                    p={4}
                    rounded={"xl"}
                    minW={"sm"}
                  >
                    <Stack>
                      <Link
                        // TODO: Add link to domain page
                        href="#"
                        role={"group"}
                        display={"block"}
                        p={2}
                        rounded={"md"}
                        _hover={{
                          bg: bgColorBaseHover,
                        }}
                      >
                        <Stack direction={"row"} align={"center"}>
                          <Box>
                            <Text
                              transition={"all .3s ease"}
                              _groupHover={{ color: "blue.500" }}
                              fontWeight={500}
                            >
                              Software
                            </Text>
                          </Box>
                          <Flex
                            transition={"all .3s ease"}
                            transform={"translateX(-10px)"}
                            opacity={0}
                            _groupHover={{
                              opacity: "100%",
                              transform: "translateX(0)",
                            }}
                            justify={"flex-end"}
                            align={"center"}
                            flex={1}
                          >
                            <Icon
                              color={"blue.500"}
                              w={5}
                              h={5}
                              as={ChevronRightIcon}
                            />
                          </Flex>
                        </Stack>
                      </Link>
                    </Stack>
                  </PopoverContent>
                </Popover>
              </Box>
              {verified && GrantedAuth.includes("Owner") && (
                <Link
                  p={2}
                  fontSize={"2xl"}
                  fontWeight={350}
                  color={textColorBase}
                  _hover={{
                    textDecoration: "underline",
                    color: textColorBaseHover,
                  }}
                  onClick={() => {
                    window.location.href = "/register/moderator";
                  }}
                >
                  Admin
                </Link>
              )}
            </HStack>
          </HStack>

          <Flex alignItems={"center"}>
            <Stack direction={"row"} spacing={7}>
              <Button onClick={toggleColorMode}>
                {colorMode === "light" ? <MoonIcon /> : <SunIcon />}
              </Button>
              {verified && appUser ? (
                <Box>
                  <Menu autoSelect={false}>
                    <MenuButton
                      as={Button}
                      rounded={"full"}
                      variant={"link"}
                      cursor={"pointer"}
                      minW={0}
                    >
                      <Avatar
                        size={"md"}
                        src={`https://avatars.dicebear.com/api/big-smile/${
                          JSON.parse(appUser).email
                        }.svg`}
                      />
                    </MenuButton>
                    <MenuList bg={bgColorBase} border={0} boxShadow={"xl"}>
                      <br />
                      <Center>
                        <Avatar
                          size={"2xl"}
                          src={`https://avatars.dicebear.com/api/big-smile/${
                            JSON.parse(appUser).email
                          }.svg`}
                        />
                      </Center>
                      <br />
                      <Center>
                        <Text fontWeight={600}>
                          {JSON.parse(appUser).firstname +
                            " " +
                            JSON.parse(appUser).lastname}
                        </Text>
                      </Center>
                      <br />
                      <MenuDivider />
                      <MenuItem
                        bg={bgColorBase}
                        _hover={{
                          bg: bgColorBaseHover,
                        }}
                        icon={<RiAccountCircleLine />}
                        onClick={() => {
                          window.location.href = "/account";
                        }}
                      >
                        Account
                      </MenuItem>
                      <MenuItem
                        bg={bgColorBase}
                        _hover={{
                          bg: bgColorBaseHover,
                        }}
                        icon={<RiLogoutBoxRLine />}
                        color={"red.400"}
                        onClick={() => {
                          logout();
                          window.location.href = "/login";
                        }}
                      >
                        Logout
                      </MenuItem>
                    </MenuList>
                  </Menu>
                </Box>
              ) : (
                <Tooltip hasArrow label="Log in" fontSize="md">
                  <Avatar
                    as={"button"}
                    size={"md"}
                    onClick={() => {
                      window.location.href = "/login";
                    }}
                  />
                </Tooltip>
              )}
            </Stack>
          </Flex>
        </Flex>
      </Box>
    </>
  );
}

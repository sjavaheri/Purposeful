"use client";
import Carousel from '@/components/Carousel';
import CollabRequestPopup from '@/components/CollabRequestPopup';
import { useState } from "react";
import {
    Box,
    chakra,
    Container,
    Stack,
    Text,
    Image,
    Flex,
    VStack,
    Button,
    Heading,
    SimpleGrid,
    StackDivider,
    useColorModeValue,
    VisuallyHidden,
    List,
    ListItem,
} from '@chakra-ui/react';

export default function Simple() {

    const [collabReq, set_req] = useState(<Flex display={"none"}></Flex>);

    function collab_rm(){
        set_req(<Flex display={"none"}></Flex>)
    }
    //TODO: Pass Idea ID and Requester mail and Idea Title
    function collab_req(){
        set_req(<CollabRequestPopup RequesterMail={""} ideaID={""} ideaTitle={""} removeFunc={collab_rm}/>);
    }
    return (
        <Box width={"100%"} height={"100%"} alignItems={"center"}>
            {collabReq}
        <Container maxW={'7xl'}>
            <SimpleGrid
                columns={{ base: 1, lg: 2 }}
                spacing={{ base: 8, md: 10 }}
                py={{ base: 18, md: 24 }}>
                <Stack>
                    <Box as={'header'}>
                        <Heading
                            lineHeight={1.1}
                            fontWeight={600}
                            fontSize={{ base: '2xl', sm: '4xl', lg: '5xl' }}>
                            Flying Cars
                        </Heading>
                        <Text
                            color={useColorModeValue('gray.900', 'gray.400')}
                            fontWeight={300}
                            fontSize={'2xl'}>
                            Let people fly in cars
                        </Text>
                    </Box>
                    <Image
                        rounded={'md'}
                        alt={'product image'}
                        src={
                            'https://c.files.bbci.co.uk/9942/production/_119143293_flying-car.jpg'
                        }
                        fit={'cover'}
                        align={'center'}
                        w={'100%'}
                        h={{ base: '100%', sm: '400px', lg: '500px' }}
                    />
                    <Button
                            rounded={'none'}
                            w={'full'}
                            mt={8}
                            size={'lg'}
                            py={'7'}
                            bg={useColorModeValue('gray.900', 'gray.50')}
                            color={useColorModeValue('white', 'gray.900')}
                            textTransform={'uppercase'}
                            _hover={{
                                transform: 'translateY(2px)',
                                boxShadow: 'lg',
                            }}
                            onClick={
                                () => collab_req()
                            }
                            >
                            Send A Collaboration Request
                        </Button>
                    {/* <Text
                        color={useColorModeValue('gray.900', 'gray.400')}
                        fontWeight={300}
                        fontSize={'3xl'}>
                        Author:
                    </Text>
                    <Text
                        color={useColorModeValue('white.900', 'white.400')}
                        fontWeight={500}
                        fontSize={'3xl'}>
                        Peter Parker
                    </Text>
                    <Image
                        rounded={'md'}
                        src={
                            'https://i.stack.imgur.com/5Kgaq.jpg?s=256&g=1'
                        }
                        fit={'cover'}
                        align={'center'}
                        w={'100%'}
                        h={{ base: '100%', sm: '400px', lg: '500px' }}
                    /> */}
                </Stack>
                <Stack spacing={{ base: 6, md: 10 }}>

                    <Stack
                        spacing={{ base: 4, sm: 6 }}
                        direction={'column'}
                        divider={
                            <StackDivider
                                borderColor={useColorModeValue('gray.200', 'gray.600')}
                            />
                        }>
                        <VStack spacing={{ base: 4, sm: 6 }}>
                            <Text
                                color={useColorModeValue('gray.500', 'gray.400')}
                                fontSize={'2xl'}
                                fontWeight={'300'}>
                                Idea Description
                            </Text>
                            <Text fontSize={'lg'}>
                                The purpose of this idea is to allow people to fly in cars. This will be done by using a turbo engine on the back of a Ferrari.
                            </Text>
                        </VStack>
                        <Box>
                            <Text
                                fontSize={{ base: '16px', lg: '18px' }}
                                color={useColorModeValue('yellow.500', 'yellow.300')}
                                fontWeight={'500'}
                                textTransform={'uppercase'}
                                mb={'4'}>
                                Is Paid?
                            </Text>

                            <SimpleGrid columns={{ base: 1, md: 1 }} spacing={10}>
                                <List spacing={2}>
                                    <ListItem>No</ListItem>
                                </List>
                            </SimpleGrid>
                        </Box>
                        <Box>
                            <Text
                                fontSize={{ base: '16px', lg: '18px' }}
                                color={useColorModeValue('yellow.500', 'yellow.300')}
                                fontWeight={'500'}
                                textTransform={'uppercase'}
                                mb={'4'}>
                                Is In Progress?
                            </Text>

                            <SimpleGrid columns={{ base: 1, md: 1 }} spacing={10}>
                                <List spacing={2}>
                                    <ListItem>No</ListItem>
                                </List>
                            </SimpleGrid>
                        </Box>
                        <Box>
                            <Text
                                fontSize={{ base: '16px', lg: '18px' }}
                                color={useColorModeValue('yellow.500', 'yellow.300')}
                                fontWeight={'500'}
                                textTransform={'uppercase'}
                                mb={'4'}>
                                Domains
                            </Text>

                            <SimpleGrid columns={{ base: 1, md: 2 }} spacing={10}>
                                <List spacing={2}>
                                    <ListItem>Software Engineering</ListItem>
                                </List>
                                <List spacing={2}>
                                    <ListItem>Agile Development</ListItem>
                                </List>
                            </SimpleGrid>
                        </Box>
                        <Box>
                            <Text
                                fontSize={{ base: '16px', lg: '18px' }}
                                color={useColorModeValue('yellow.500', 'yellow.300')}
                                fontWeight={'500'}
                                textTransform={'uppercase'}
                                mb={'4'}>
                                Topics
                            </Text>

                            <SimpleGrid columns={{ base: 1, md: 2 }} spacing={10}>
                                <List spacing={2}>
                                    <ListItem>Money</ListItem>
                                    <ListItem>Cars</ListItem>{' '}
                                    <ListItem>Electricity</ListItem>
                                </List>
                                <List spacing={2}>
                                    <ListItem>Speed</ListItem>
                                    <ListItem>Live Programming</ListItem>
                                    <ListItem>Embedded Systems</ListItem>
                                </List>
                            </SimpleGrid>
                        </Box>
                        <Box>
                            <Text
                                fontSize={{ base: '16px', lg: '18px' }}
                                color={useColorModeValue('yellow.500', 'yellow.300')}
                                fontWeight={'500'}
                                textTransform={'uppercase'}
                                mb={'4'}>
                                Technologies
                            </Text>

                            <SimpleGrid columns={{ base: 1, md: 2 }} spacing={10}>
                                <List spacing={2}>
                                    <ListItem>Python</ListItem>
                                </List>
                                <List spacing={2}>
                                    <ListItem>Gherkin</ListItem>
                                </List>
                            </SimpleGrid>
                        </Box>
                        <Button
                            rounded={'none'}
                            w={'full'}
                            mt={8}
                            size={'lg'}
                            py={'7'}
                            bg={useColorModeValue('gray.900', 'gray.50')}
                            color={useColorModeValue('white', 'gray.900')}
                            textTransform={'uppercase'}
                            _hover={{
                                transform: 'translateY(2px)',
                                boxShadow: 'lg',
                            }}>
                            High Five
                        </Button>
                    </Stack>
                    {/* <Carousel /> can't fix it for now*/}
                </Stack>
            </SimpleGrid>
        </Container>
        </Box>
    );
}
"use client";
import { useState } from 'react';
import Carousel from '@/components/Carousel';
import HighFiveBtn from '@/components/HighFiveBtn';
import CollabRequestPopup from '@/components/CollabRequestPopup';
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
import { BsSend } from "react-icons/bs";

export default function moreDetailsOfIdea(ideaId, idea) {

    const [collabReq, set_req] = useState(<Flex display={"none"}></Flex>);

    //TODO: Get requester mail
    function getReqMail(){

    }

    function collab_rm(){
        set_req(<Flex display={"none"}></Flex>)
    }
    
    function collab_req(){
        set_req(<CollabRequestPopup RequesterMail={getReqMail()} ideaID={ideaId} ideaTitle={idea.title} removeFunc={collab_rm}/>);
    }

    let domains1 = [];
    const domains2 = [];

    let i = 0;
    for (const domain in idea.domains) {
        if (i < idea.domains.length / 2) {
            domains1.push(domain.name);
        }
        else {
            domains2.push(domain.name);
        }
        i++;
    }

    const topics1 = [];
    const topics2 = [];
    i = 0;
    for (const topic in idea.topics) {
        if (i < idea.topics.length / 2) {
            topics1.push(topic.name);
        }
        else {
            topics2.push(topic.name);
        }
        i++;
    }

    const techs1 = [];
    const techs2 = [];
    i = 0;
    for (const tech in idea.techs) {
        if (i < idea.techs.length / 2) {
            techs1.push(tech.name);
        }
        else {
            techs2.push(tech.name);
        }
        i++;
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
                            {idea.title}
                        </Heading>
                        <Text
                            color={useColorModeValue('gray.900', 'gray.400')}
                            fontWeight={300}
                            fontSize={'2xl'}>
                            {idea.purpose}
                        </Text>
                    </Box>
                    <Image
                        rounded={'md'}
                        alt={'product image'}
                        src={idea.iconUrl}
                        fit={'cover'}
                        align={'center'}
                        w={'100%'}
                        h={{ base: '100%', sm: '400px', lg: '500px' }}
                    />
                    <Carousel imgUrls={idea.imgUrls} />
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
                                {idea.description}
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
                                    <ListItem>{idea.isPaid}</ListItem>
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
                                    <ListItem>{idea.isInProgress}</ListItem>
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
                                    {domains1.map((domain) => (
                                        <ListItem>{domain}</ListItem>
                                    ))}
                                </List>
                                <List spacing={2}>
                                    {domains2.map((domain) => (
                                        <ListItem>{domain}</ListItem>
                                    ))}
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
                                    {topics1.map((topic) => (
                                        <ListItem>{topic}</ListItem>
                                    ))}
                                </List>
                                <List spacing={2}>
                                    {topics2.map((topic) => (
                                        <ListItem>{topic}</ListItem>
                                    ))}
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
                                    {techs1.map((tech) => (
                                        <ListItem>{tech}</ListItem>
                                    ))}
                                </List>
                                <List spacing={2}>
                                    {techs2.map((tech) => (
                                        <ListItem>{tech}</ListItem>
                                    ))}
                                </List>
                            </SimpleGrid>
                        </Box>
                        <HighFiveBtn />
                    </Stack>
                        <Button
                            rightIcon={<BsSend/>}
                            onClick={
                                () => collab_req()
                            }
                            >
                            SEND COLLABORATION REQUEST
                        </Button>
                    {/* <Carousel /> can't fix it for now*/}
                </Stack>
            </SimpleGrid>
        </Container>
        </Box>
    );
}
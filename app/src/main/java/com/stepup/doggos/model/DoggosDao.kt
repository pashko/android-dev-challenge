/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stepup.doggos.model

import com.stepup.doggos.model.Doggo.Id
import kotlinx.coroutines.delay
import javax.inject.Inject

class DoggosDao @Inject constructor() {

    suspend fun list(): List<Map.Entry<Id, Doggo.Short>> {
        delay(100)
        return doggosShort.entries.toList()
    }

    suspend fun getShortFor(id: Id): Doggo.Short? {
        delay(100)
        return doggosShort[id]
    }

    suspend fun getDetailsFor(id: Id): Doggo.Details? {
        delay(100)
        return doggosDetails[id]
    }

    private val doggosShort = mapOf(
        Id("1") to Jack.short,
        Id("2") to Cooper.short,
        Id("3") to Doreen.short,
        Id("4") to Gwen.short,
        Id("5") to Pudding.short,
        Id("6") to Sadie.short,
        Id("7") to Lexi.short,
        Id("8") to Hulk.short,
    )

    private val doggosDetails = mapOf(
        Id("1") to Jack.details,
        Id("2") to Cooper.details,
        Id("3") to Doreen.details,
        Id("4") to Gwen.details,
        Id("5") to Pudding.details,
        Id("6") to Sadie.details,
        Id("7") to Lexi.details,
        Id("8") to Hulk.details,
    )
}

// Mock data taken from https://www.dogstrust.org.uk/rehoming/dogs/

object Jack {
    val short = Doggo.Short(
        name = "Jack",
        age = 2..5,
        imageUrl = Url("https://www.dogstrust.org.uk/dogimages/1243913_jack_20210226093247_jack_800.jpg")
    )
    val details = Doggo.Details(
        breed = Doggo.Breed("Jack Russell Terrier"),
        sex = Doggo.Sex.Male,
        likes = "Jack is a very energetic 2 year old Jack Russell Terrier looking for an active home. Jack loves to go out exploring and sniffing around in the woods, and burning off some energy running around our field, a typical terrier he just follows his nose and is far more interested in what's around him than saying hello to anyone and prefers to just do his own thing. Jack loves getting some extra enrichment to keep his brain busy, and enjoys playing with his toys, especially chasing a ball or a game of tug. Jack has a very high chase drive and will need further training in his new home.",
        typeOfHome = "Jack is looking for an active adult only home where he is the only pet. A secure garden of his own is essential for Jack, along with easy access to walks where there aren't too many other dogs. Jack's adopters must be committed to continue his training as advised by our behaviour team, and need to be within a 45 minute drive of the Canterbury Rehoming Centre as he will need multiple meets here when restrictions allow, as well as to be taken out on home visits with our behavuiour team.",
        aboutMe = "*Due to current restrictions, I can't go to my forever home just yet, but you can contact the rehoming centre to register your interest. When restrictions ease, and if we're a suitable match, you will be able to discuss me in more detail with my carers at the centre. Thank you so much for your interest in me! * If Jack sounds like your perfect match please get in contact to express an interest. Please note if you rent your property you must be able to provide written permission from your landlord to keep a dog to complete the rehome process. If we receive high levels of interest you will only hear back from us if we are able to proceed with your application. We will conduct a rehoming interview with successful applicants and home visit via video. Access to emails and video technology will be essential for this process.",
        url = Url("https://www.dogstrust.org.uk/rehoming/dogs/dog/1243913/jack")
    )
}

object Cooper {
    val short = Doggo.Short(
        name = "Cooper",
        age = 2..5,
        imageUrl = Url("https://www.dogstrust.org.uk/dogimages/1244901_cooper_20210225033152_cooper1_800.jpg")
    )
    val details = Doggo.Details(
        breed = Doggo.Breed("American Bulldog"),
        sex = Doggo.Sex.Male,
        likes = "Cooper is a 4 year old American Bulldog cross. Due to current restrictions, I can't go to my forever home just yet, but you can contact the rehoming centre to register your interest. When restrictions ease, and if we're a suitable match, you will be able to discuss me in more detail with my carers at the centre. Cooper is a handsome big lad who adores attention from his family. He is happy to meet doggy friends out on his walks but would like to be the only dog in the home.",
        typeOfHome = "Cooper is looking for owners who have experience with owning bigger breeds. Cooper is very strong on lead and will need owners who can physically handle his strength on lead. He would love his very own secure garden with direct access where he can run around and spend time with his family. He can live with children of 12 years and above.",
        aboutMe = "Cooper is a sweet and affectionate boy. He is very active and loves playing fetch with his tennis ball. He will need owners happy to keep up training. Cooper loves to be cosied up next to you on the couch and enjoys a tasty treat!",
        url = Url("https://www.dogstrust.org.uk/rehoming/dogs/dog/1244901/cooper")
    )
}

object Doreen {
    val short = Doggo.Short(
        name = "Doreen",
        age = 1..2,
        imageUrl = Url("https://www.dogstrust.org.uk/dogimages/1244883_doreen_20210225030208_doreen-1_800.jpg")
    )
    val details = Doggo.Details(
        breed = Doggo.Breed("Rottweiler"),
        sex = Doggo.Sex.Female,
        likes = "Doreen is a gorgeous 1 year old Rottweiler looking for a home to call her own. She's a great girl who loves to spend time with her human friends, she especially loves when they give her a tasty treat! Doreen loves to get out and about where she can have a good explore and sniff around.",
        typeOfHome = "Doreen can meet other dogs, but she will react at smaller ones so it would suit her best to live in a quieter dog area with her own secure garden. Rottweiler experience would be preferable and owners will need to be comfortable with her strength. Doreen can be worried by heavy traffic and cyclists/joggers so will need to live away from busy roads or footpaths. She can live with children aged 14 years and over who are comfortable with larger dogs. She will need to be the only pet in the home.",
        aboutMe = "Please note, Doreen's new home will need to be within half an hour from the centre. To apply for Doreen please click on the 'Come and meet me' button below. Applications will close on Friday 26th February at 12pm.",
        url = Url("https://www.dogstrust.org.uk/rehoming/dogs/dog/1244883/doreen")
    )
}

object Gwen {
    val short = Doggo.Short(
        name = "Gwen",
        age = 8..Int.MAX_VALUE,
        imageUrl = Url("https://www.dogstrust.org.uk/dogimages/1244097_gwen_20210226084240_gwen-pug_800.jpg")
    )
    val details = Doggo.Details(
        breed = Doggo.Breed("Pug"),
        sex = Doggo.Sex.Female,
        likes = "I am currently looking for my new family. To apply to rehome me please click on the 'Come and Meet Me' button below. Applications will close when a suitable match is found. Gwen's home will need to be within one and half hour's drive from the centre.",
        typeOfHome = "Gwen is an 8 year old Jack Russell Terrier Cross Pug. Gwen is a sweet girl who can be nervous in new situations but soon comes around. When she knows you, she loves to have a fuss and play with her toys. Gwen can be worried with intense handling, such as at the vets, so will need help and ongoing training to feel comfortable in these situations. She can live with children aged 14 years and over.",
        aboutMe = "Gwen gets on with calm dogs and is happy to greet them on walks but is worried by bigger and boisterous dogs and can be vocal on lead towards them. She would prefer to be the only dog in the home, we are unsure if she can live with cats. Gwen may need reminding with her toilet training and any time being left will need to be done gradually. Once settled, Gwen will make a loving addition to the home.",
        url = Url("https://www.dogstrust.org.uk/rehoming/dogs/dog/1244097/gwen")
    )
}

object Pudding {
    val short = Doggo.Short(
        name = "Pudding",
        age = 8..Int.MAX_VALUE,
        imageUrl = Url("https://www.dogstrust.org.uk/dogimages/1161949_pudding_20210225024751_pudding-1_800.jpg")
    )
    val details = Doggo.Details(
        breed = Doggo.Breed("Spaniel Cross"),
        sex = Doggo.Sex.Male,
        likes = "Pudding is an 8 year old cocker spaniel who is still an active boy who loves his walks and playing with his toys, tennis balls and squeaky toys are his favourite. He enjoys exploring and can go for walks with other dogs, but he must be the only pet in the home. Pudding can greet people nicely but needs management for close handling such as vet examinations and grooming.",
        typeOfHome = "Pudding is looking for an active home with a maximum of 3 adults and minimal visitors. He must have direct access to a secure garden of his own and will need to be the only pet as he does not share his belongings. Pudding is going to need some ongoing training and management, so potential adopters must be committed to following the advice of our behaviour team and continuing his training. His new family will need to meet him multiple times at the Canterbury Rehoming Centre when restrictions allow this, and he will also need visits to the home with our behaviour team, so we will only be accepting applications from people who live within a 45 minute drive.",
        aboutMe = "*Due to current restrictions, I can't go to my forever home just yet, but you can contact the rehoming centre to register your interest. When restrictions ease, and if we're a suitable match, you will be able to discuss me in more detail with my carers at the centre. Thank you so much for your interest in me! * If Pudding sounds like your perfect match please get in contact to express an interest. Please note if you rent your property you must be able to provide written permission from your landlord to keep a dog to complete the rehome process. If we receive high levels of interest you will only hear back from us if we are able to proceed with your application. We will conduct a rehoming interview with successful applicants and home visit via video. Access to emails and video technology will be essential for this process.",
        url = Url("https://www.dogstrust.org.uk/rehoming/dogs/dog/1161949/pudding")
    )
}

object Sadie {
    val short = Doggo.Short(
        name = "Sadie",
        age = 8..Int.MAX_VALUE,
        imageUrl = Url("https://www.dogstrust.org.uk/dogimages/1243427_sadie_20210226083309_sadie-collie-x-spaniel-(3)_800.jpg")
    )
    val details = Doggo.Details(
        breed = Doggo.Breed("Collie Cross"),
        sex = Doggo.Sex.Female,
        likes = "I am currently looking for my new family. To apply to rehome me please click on the 'Come and Meet Me' button below. Applications will close once I have found a suitable match. Due to Sadie's medical issues, her home will need to be within 45 minutes of the Centre, so that she can have regular vet checks initially.",
        typeOfHome = "Sadie is and 8 and half year-old Collie cross Spaniel. She is a loveable girl and is very happy and wiggly girl when greeting people. Sadie has not spent much time around other animals in the past so is best suited as the only pet in the home but would enjoy having dog friends to meet up with to go on walks.",
        aboutMe = "Sadie has shown worry in her previous home around her food so she will need to be given space at mealtimes and children in the home must be 14 years and over. Being both a Collie and Spaniel, Sadie will need lots of mental enrichment to keep her brain busy. Sadie does require a secure garden and she has previously jumped a 4ft fence. Sadie is currently recovering from a leg operation; she will initially go out on our Home from Home scheme and would have support from the Dogs Trust Bridgend team. Sadie will make a fun and loving addition to her new family.",
        url = Url("https://www.dogstrust.org.uk/rehoming/dogs/dog/1243427/sadie")
    )
}

object Lexi {
    val short = Doggo.Short(
        name = "Lexi",
        age = 5..7,
        imageUrl = Url("https://www.dogstrust.org.uk/dogimages/1244608_lexi_20210224035717_lexi-608-1_800.jpg")
    )
    val details = Doggo.Details(
        breed = Doggo.Breed("Akita"),
        sex = Doggo.Sex.Female,
        likes = "7 year old Lexi has the most adorable head tilt and it seems as though she takes in every word you say! She's typically aloof most of the time, something any Akita fan will know, but she does enjoy attention and a gentle fuss, especially a good bum scratch.",
        typeOfHome = "Lexi isn't too interested in snacks or toys at the moment, she prefers to happily watch the coming and goings of the centre from her bed. She would be happiest as the only pet home; she will walk with quiet dogs but chooses minimal interaction. We know that Lexi will chase cats so those are definitely a no! Lexi doesn't enjoy any particularly invasive interactions with people, so no hugging or anything that makes her feel uncomfortable. For this reason it's better that any children she lives with are of an upper secondary school age. Lexi is perfectly house trained and can spend an hour or two by herself without worry.",
        aboutMe = null,
        url = Url("https://www.dogstrust.org.uk/rehoming/dogs/dog/1244608/lexi")
    )
}

object Hulk {
    val short = Doggo.Short(
        name = "Hulk",
        age = 0..1,
        imageUrl = Url("https://www.dogstrust.org.uk/dogimages/1244017_hulk_20210224044338_hulk-in-foster-(5)_800.jpg")
    )
    val details = Doggo.Details(
        breed = Doggo.Breed("A Crossbreed"),
        sex = Doggo.Sex.Male,
        likes = "I am currently looking for my new family. To apply to rehome me please click on the 'Come and Meet Me' button below. Applications will close on Friday 26th February at 4pm. Please be aware that applications may close early if we receive a high level of applications. Due to the current lockdown restrictions, we can only accept applications within an hour and a half hour drive of the centre.",
        typeOfHome = "Hulk is a 19 week old Huntaway Crossbreed. He will grow up to be a large dog so we are looking for an experienced home who can continue with his training. Hulk came into Dogs Trust with a leg injury and unfortunately had to have his leg removed, however he has been recovering well in a foster home and is adjusting to life with three legs.",
        aboutMe = "Hulk is a typical puppy and does not realize his own size and will try and fit on your lap for cuddles when ever possible. Any children in the home will need to be 11 years and over. Hulk loves having another dog as company and gains confidence from them, so must live with a calm, confident dog already in the home who will act as a good role model. We are unsure if he can live with cats. Hulk will need continued training with lead walking, building his confidence and toilet training. Hulk will bring lots of fun and energy to his new family.",
        url = Url("https://www.dogstrust.org.uk/rehoming/dogs/dog/1244017/hulk")
    )
}

# YPBot
Twitter bot that responds to tweets asking for shopping advice by tweeting back results of the closest merchants from the location of the original tweet.
The query from the user (tweet) will be retrieved from Twitter's API along with its geolocation.
Then, the tweet will pass through Microsoft's nature language API and retrieve the purchase keyword (food, clothes, electronics).
The YellowPage API will take that keyword and respond with relevant businesses closest to the location of original tweet.
The bot will answer the user with a tweet containing a link to a website with the most relevant results.

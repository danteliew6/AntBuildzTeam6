# AntBuildzHeroku
## AntBuildz Team 6 Closed Bidding System

### How to deploy:
1. Create a GitHub repository and copy the all the folders from this repository there.
2. Open up application.properties under src/main/resources
3. Replace the following details with your chosen MySQL database details:
    * spring.datasource.url=jdbc:mysql://{MYSQL DATABASE ENDPOINT URL}
	* spring.datasource.username={MYSQL USERNAME}
	* spring.datasource.password={MYSQL PASSWORD}
	* server.port=${PORT:8080}
4. Commit and push the changes to your GitHub repository
5. Head to Heroku and create an account.
6. Log in, click on the Deploy tab, and connect to your GitHub account
7. Select the GitHub repository created in step 1, and click Deploy Branch on the Manual Deploy section.
8. Deployment is done!

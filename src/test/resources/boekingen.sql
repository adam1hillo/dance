insert into boekingen (naam, aantalTickets, festivalId) values
('boekingTest1', 1, (select id from festivals where naam = 'test1')),
('boekingTest2', 2, (select id from festivals where naam = 'test2'));
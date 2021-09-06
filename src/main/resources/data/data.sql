INSERT INTO shopdb.`user` (active,password,username) VALUES
                                                         (1,'$2a$10$mFDMAS97z0qhFmLImuahgeJPfygFI4gQo8cgiJkWRGS90ObTrG9hO','admin'),
                                                         (1,'$2a$10$pyjoHwXHC/SvB1H17Arq.u3.jsdAJUsFpoTtvts2Ko4iNgn7GVQ6O','customer'),
                                                         (1,'$2a$10$sCsmVvCdXKWaHb12cgjIJONMi5w01UB5zSX/y38apMuZVw6SqdPw.','customer1'),
                                                         (1,'$2a$10$cX5t155CRwBfFU2jarSWheoYu00wCAf0lmRm.xoy7X8PZYcqHxB3.','customer2'),
                                                         (1,'$2a$10$ZZG2vvpVQg80uEm.tCHvCOZC8ye1XbBcZ1SXAip8wIqGTsshWtGbi','customer3'),
                                                         (1,'$2a$10$gW4BlANdulSohbCFiqlWSeDpoK4BhaF.1mgmea9rZdrjp1uqprlES','customer4'),
                                                         (1,'$2a$10$xPKbyl5qCiu4b8DlhWokZebG2jepFixVlMO7kTgjHyyp9xhCyCMOK','customer5');
INSERT INTO shopdb.`role` (`role`) VALUES
                                       ('ROLE_ADMIN'),
                                       ('ROLE_CUSTOMER');

INSERT INTO shopdb.user_role (user_id,role_id) VALUES
                                                   (1,1),
                                                   (1,2),
                                                   (2,2),
                                                   (3,2),
                                                   (4,2),
                                                   (5,2),
                                                   (6,2),
                                                   (7,2);
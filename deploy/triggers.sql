DELIMITER $$
DROP TRIGGER IF EXISTS balance_insert_trigger
$$
CREATE TRIGGER balance_insert_trigger
     BEFORE INSERT ON `southwesttech`.`bankaccount` FOR EACH ROW
     BEGIN
          IF NEW.balance < 0
          THEN
               SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Cannot add/update with negative balance';
          END IF;
     END;
$$

DROP TRIGGER IF EXISTS balance_update_trigger
$$
CREATE TRIGGER balance_update_trigger
     BEFORE UPDATE ON `southwesttech`.`bankaccount`  FOR EACH ROW
     BEGIN
          IF NEW.balance < 0 
          THEN
               SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Cannot add/update with negative balance';
          END IF;
     END;
$$
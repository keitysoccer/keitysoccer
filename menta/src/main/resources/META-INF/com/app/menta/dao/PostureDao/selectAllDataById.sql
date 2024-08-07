SELECT
  id,
  5 as position,
  5 as posture_position,
  results as posture,
  0 as achieve_flg
FROM
  RESULTS
WHERE
  id = /* id */0
UNION
SELECT
  id,
  position,
  5 as posture_position,
  ability as posture,
  0 as achieve_flg
FROM
  ABILITY
WHERE
  id = /* id */0
UNION
SELECT
  id,
  5 as position,
  position as posture_position,
  ability as posture,
  0 as achieve_flg
FROM
  ABILITY
WHERE
  id = /* id */0
UNION
SELECT
  id,
  position,
  posture_position,
  posture,
  achieve_flg
FROM
  POSTURE
WHERE
  id = /* id */0
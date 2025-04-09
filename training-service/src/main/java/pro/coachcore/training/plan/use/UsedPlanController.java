package pro.coachcore.training.plan.use;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UsedPlanController {
  private final UsedPlanRepository usedPlanRepository;
  private final UsedPlanMapper usedPlanMapper;

}
